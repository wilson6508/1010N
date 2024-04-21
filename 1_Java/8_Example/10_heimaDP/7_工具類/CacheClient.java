package com.hmdp.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.hmdp.utils.RedisConstants.CACHE_NULL_TTL;
import static com.hmdp.utils.RedisConstants.LOCK_SHOP_KEY;

@Slf4j
@Component
public class CacheClient {

    private final StringRedisTemplate stringRedisTemplate;

    public CacheClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }

    public <R, ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallBack, Long time, TimeUnit unit) {
        // 1. 從redis查詢緩存
        String key = keyPrefix + id;
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 存在 > 反序列化 > return
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, type);
        }
        // json不為null但是為"" (避免緩存穿透)
        if (json != null) {
            return null;
        }
        // 2. 緩存不存在 > 根據id查詢db
        R r = dbFallBack.apply(id);
        // db也不存在
        if (r == null) {
            // 將空字串寫入redis (避免緩存穿透)
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回錯誤訊息
            return null;
        }
        // db存在 > 寫入redis
        this.set(key, r, time, unit);
        return r;
    }

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    public <R, ID> R queryWithLogicalExpire(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallBack, Long time, TimeUnit unit) {
        // 1. 從redis查詢緩存
        String key = keyPrefix + id;
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 不存在 > return
        if (StrUtil.isBlank(json)) {
            return null;
        }
        // 3. 存在 > 判斷expireTime
        RedisData redisData = JSONUtil.toBean(json, RedisData.class);
        R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
        LocalDateTime expireTime = redisData.getExpireTime();
        // 尚未過期
        if (expireTime.isAfter(LocalDateTime.now())) {
            return r;
        }
        // 已過期 > 取互斥鎖 > 緩存重建
        String lockKey = LOCK_SHOP_KEY + id;
        boolean isLock = tryLock(lockKey);
        // 取鎖成功
        if (isLock) {
            // 開線呈去做緩存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 查詢db
                    R apply = dbFallBack.apply(id);
                    // 寫入redis
                    this.setWithLogicalExpire(key, apply, time, unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    // 釋放鎖
                    unlock(lockKey);
                }
            });
        }
        // 返回"過期的"
        return r;
    }

    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }
}
