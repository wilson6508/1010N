package com.hmdp.utils;

import cn.hutool.core.lang.UUID;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleRedisLock implements ILock {

    public static final String KEY_PREFIX = "lock:";
    public static final String ID_PREFIX = UUID.randomUUID().toString(true) + "-";
    private String name;
    private StringRedisTemplate stringRedisTemplate;
    public static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public boolean tryLock(long timeoutSec) {
        // 獲取線程標示
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        // 獲取鎖
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId, timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unLock() {
        // 調用lua腳本
        List<String> keys = Collections.singletonList(KEY_PREFIX + name);
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        stringRedisTemplate.execute(UNLOCK_SCRIPT, keys, threadId);
    }

//    @Override
//    public void unLock() {
//        // 取得線程標示
//        String threadId = ID_PREFIX + Thread.currentThread().getId();
//        // 取得鎖的value
//        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
//        // 釋放鎖
//        if (threadId.equals(id)) {
//            stringRedisTemplate.delete(KEY_PREFIX + name);
//        }
//    }

}
