package com.hmdp.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.hmdp.utils.CacheClient;
import com.hmdp.utils.RedisData;
import com.hmdp.utils.SystemConstants;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.hmdp.utils.RedisConstants.*;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private CacheClient cacheClient;

    @Override
    public Result queryById(Long id) {

        // 緩存穿透
        // Shop shop = queryWithPassThrough(id);
        // Shop shop = cacheClient.queryWithPassThrough(CACHE_SHOP_KEY, id, Shop.class, this::getById, CACHE_SHOP_TTL, TimeUnit.MINUTES);

        // 互斥鎖解決緩存擊穿
        // Shop shop = queryWithMutex(id);

        // 邏輯過期解決緩存擊穿
        // Shop shop = queryWithLogicalExpire(id);
        // Shop shop = cacheClient.queryWithLogicalExpire(CACHE_SHOP_KEY, id, Shop.class, this::getById, 20L, TimeUnit.SECONDS);
        Shop shop = cacheClient.queryWithLogicalExpire(CACHE_SHOP_KEY, id, Shop.class, this::getById, CACHE_SHOP_TTL, TimeUnit.MINUTES);

        if (shop == null) {
            return Result.ok("店鋪不存在");
        }
        return Result.ok(shop);
    }

    public Shop queryWithPassThrough(Long id) {
        // 1. 從redis查詢緩存
        String key = CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(shopJson)) {
            return JSONUtil.toBean(shopJson, Shop.class);
        }
        if (shopJson != null) {
            return null;
        }
        // 2. 緩存不存在 > 根據id查詢db
        Shop shop = getById(id);
        // db也不存在
        if (shop == null) {
            // 將空值寫入redis
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回錯誤訊息
            return null;
        }
        // db存在 > 寫入redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return shop;
    }

    public Shop queryWithMutex(Long id) {
        // 1. 從redis查詢緩存
        String key = CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(key);
        // 2. redis中存在店鋪資訊
        if (StrUtil.isNotBlank(shopJson)) {
            return JSONUtil.toBean(shopJson, Shop.class);
        }
        // 3. redis中存在空字串(避免緩存穿透)
        if (shopJson != null) {
            return null;
        }
        // 4. 緩存中沒有店鋪資訊 > 緩存重建
        // 4.1 互斥鎖
        String lockKey = LOCK_SHOP_KEY + id;
        Shop shop = null;
        try {
            boolean isLock = tryLock(lockKey);
            // 取鎖失敗 > 休眠 > 重試
            if (!isLock) {
                Thread.sleep(50);
                return queryWithMutex(id);
            }
            // 取鎖成功 > 根據id查詢db
            shop = getById(id);
            // 模擬重建的耗費時間
            Thread.sleep(200);
            // db也不存在
            if (shop == null) {
                // 將空值寫入redis
                stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
                // 返回錯誤訊息
                return null;
            }
            // db存在 > 寫入redis
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 5. 釋放互斥鎖
            unlock(lockKey);
        }
        return shop;
    }

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    public void saveShopToRedis(Long id, Long expireSeconds) {
        Shop shop = getById(id);
        RedisData redisData = new RedisData();
        redisData.setData(shop);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(expireSeconds));
        stringRedisTemplate.opsForValue().set(CACHE_SHOP_KEY + id, JSONUtil.toJsonStr(redisData));
    }

    public Shop queryWithLogicalExpire(Long id) {
        // 1. 從redis查詢緩存
        String key = CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(key);
        // 2. 未命中
        if (StrUtil.isBlank(shopJson)) {
            return null;
        }
        // 3. 命中 > 判斷expireTime
        RedisData redisData = JSONUtil.toBean(shopJson, RedisData.class);
        Shop shop = JSONUtil.toBean((JSONObject) redisData.getData(), Shop.class);
        LocalDateTime expireTime = redisData.getExpireTime();
        // 尚未過期
        if (expireTime.isAfter(LocalDateTime.now())) {
            return shop;
        }
        // 已過期 > 取互斥鎖 > 緩存重建
        String lockKey = LOCK_SHOP_KEY + id;
        boolean isLock = tryLock(lockKey);
        // 取鎖成功
        if (isLock) {
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 開線呈去做緩存重建
                    Thread.sleep(100);
                    this.saveShopToRedis(id, 20L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    // 釋放鎖
                    unlock(lockKey);
                }
            });
        }
        return shop;
    }

    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    @Transactional
    public Result update(Shop shop) {
        Long id = shop.getId();
        if (id == null) {
            return Result.fail("店鋪id不能為空");
        }
        // 更新db
        updateById(shop);
        // 刪緩存
        stringRedisTemplate.delete(CACHE_SHOP_KEY + shop.getId());
        return Result.ok();
    }

    @Override
    public Result queryShopByType(Integer typeId, Integer current, Double x, Double y) {
        if (x == null || y == null) {
            Page<Shop> page = query().eq("type_id", typeId).page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
            return Result.ok(page.getRecords());
        }
        // 計算分頁
        int from = (current - 1) * SystemConstants.DEFAULT_PAGE_SIZE;
        int end = current * SystemConstants.DEFAULT_PAGE_SIZE;
        // 查詢redis 依照距離排序 (shopId, distance)
        String key = SHOP_GEO_KEY + typeId;
        // geosearch key bylonlat x y byradius 10 withdistance
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = stringRedisTemplate.opsForGeo()
                .search(key, GeoReference.fromCoordinate(x, y), new Distance(5000), // 5000m
                        RedisGeoCommands.GeoRadiusCommandArgs.newGeoSearchArgs().includeDistance().limit(end));
        if (results == null) {
            return Result.ok(Collections.emptyList());
        }
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = results.getContent();
        // 截取from ~ end
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> skip = content.stream().skip(from).collect(Collectors.toList());
        if (skip.size() == 0) {
            return Result.ok(Collections.emptyList());
        }
        List<Long> shopIdList = new ArrayList<>(skip.size());
        Map<Long, Distance> distanceMap = new HashMap<>(skip.size());
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> entity : skip) {
            Long shopId = Long.parseLong(entity.getContent().getName());
            shopIdList.add(shopId);
            distanceMap.put(shopId, entity.getDistance());
        }
        // 根據shipId查詢店家資訊
        String join = shopIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
        List<Shop> shopList = query().in("id", shopIdList).last("ORDER BY FIELD(id," + join + ")").list();
        for (Shop shop : shopList) {
            shop.setDistance(distanceMap.get(shop.getId()).getValue());
        }
        return Result.ok(shopList);
    }


}
