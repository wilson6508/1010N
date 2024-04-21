package com.hmdp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    private RLock lock;

    @BeforeEach
    void setUp() {
        lock = redissonClient.getLock("order");
    }

    @Test
    void method1() {
        boolean isLock = lock.tryLock();
        if (!isLock) {
            log.error("取鎖失敗...1");
            return;
        }
        try {
            log.info("取鎖成功...1");
            method2();
            log.info("執行業務...1");
        } finally {
            log.warn("釋放鎖...1");
            lock.unlock();
        }
    }

    @Test
    void method2() {
        boolean isLock = lock.tryLock();
        if (!isLock) {
            log.error("取鎖失敗...2");
            return;
        }
        try {
            log.info("取鎖成功...2");
            log.info("執行業務...2");
        } finally {
            log.warn("釋放鎖...2");
            lock.unlock();
        }
    }



}
