package com.hmdp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.hmdp.utils.RedisIdWorker;
import com.hmdp.utils.SimpleRedisLock;
import com.hmdp.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Slf4j
@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {

    @Resource
    private ISeckillVoucherService seckillVoucherService;
    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonClient redissonClient;

    public static final DefaultRedisScript<Long> SECKILL_SCRIPT;
    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    // 阻塞對列: 當1個線程嘗試從對列中獲取元素 若無元素 > 該線程阻塞 直到隊列中有元素 線程才會被喚醒 並獲取元素
    // private BlockingQueue<VoucherOrder> orderTasks = new ArrayBlockingQueue<>(1024 * 1024);
    private static final ExecutorService SECKILL_ORDER_EXECUTOR = Executors.newSingleThreadExecutor();

    @PostConstruct // 當前類初始化後完畢後執行
    private void init() {
//        SECKILL_ORDER_EXECUTOR.submit(new VoucherOrderHandler());
    }

    private class VoucherOrderHandler implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String queueName = "stream.orders";
                    // 1. 取得MQ中的訂單資訊
                    List<MapRecord<String, Object, Object>> list = stringRedisTemplate.opsForStream().read(
                            Consumer.from("g1", "c1"),
                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)),
                            StreamOffset.create(queueName, ReadOffset.lastConsumed())
                    );
                    // 2. 消息取得失敗
                    if (CollectionUtils.isEmpty(list)) {
                        continue;
                    }
                    // 3. 消息取得成功
                    MapRecord<String, Object, Object> record = list.get(0);
                    Map<Object, Object> values = record.getValue();
                    long userId = Long.parseLong(values.get("userId").toString());
                    long voucherId = Long.parseLong(values.get("voucherId").toString());
                    long id = Long.parseLong(values.get("id").toString());
                    VoucherOrder voucherOrder = new VoucherOrder();
                    voucherOrder.setUserId(userId);
                    voucherOrder.setVoucherId(voucherId);
                    voucherOrder.setId(id);
                    handleVoucherOrder(voucherOrder);
                    // 4. ACK確認
                    stringRedisTemplate.opsForStream().acknowledge(queueName, "g1", record.getId());
                } catch (Exception e) {
                    log.error("處理訂單異常", e);
                    handlePendingList();
                }
            }
        }
    }

//    private class VoucherOrderHandler implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    VoucherOrder voucherOrder = orderTasks.take();
//                    handleVoucherOrder(voucherOrder);
//                } catch (InterruptedException e) {
//                    log.error("處理訂單異常", e);
//                }
//            }
//        }
//    }

    private void handleVoucherOrder(VoucherOrder voucherOrder) {
        // 注意 此為子線程執行的方法 沒辦法取ThreadLocal的東西 包含UserHolder AopContext
        Long voucherId = voucherOrder.getVoucherId();
        seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherId)
                .gt("stock", 0)
                .update();
        save(voucherOrder);
    }

    private void handlePendingList() {
        while (true) {
            try {
                String queueName = "stream.orders";
                // 1. 取得pending-list中的訂單資訊
                List<MapRecord<String, Object, Object>> list = stringRedisTemplate.opsForStream().read(
                        Consumer.from("g1", "c1"),
                        StreamReadOptions.empty().count(1),
                        StreamOffset.create(queueName, ReadOffset.from("0"))
                );
                // 2. pending-list中沒有異常消息
                if (CollectionUtils.isEmpty(list)) {
                    break;
                }
                // 3. 消息取得成功
                MapRecord<String, Object, Object> record = list.get(0);
                Map<Object, Object> values = record.getValue();
                long userId = Long.parseLong(values.get("userId").toString());
                long voucherId = Long.parseLong(values.get("voucherId").toString());
                long id = Long.parseLong(values.get("id").toString());
                VoucherOrder voucherOrder = new VoucherOrder();
                voucherOrder.setUserId(userId);
                voucherOrder.setVoucherId(voucherId);
                voucherOrder.setId(id);
                handleVoucherOrder(voucherOrder);
                // 4. ACK確認
                stringRedisTemplate.opsForStream().acknowledge(queueName, "g1", record.getId());
            } catch (Exception e) {
                log.error("處理pending-list異常", e);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public Result seckillVoucher(Long voucherId) {
        Long userId = UserHolder.getUser().getId();
        long orderId = redisIdWorker.nextId("order");
        Long result = stringRedisTemplate.execute(SECKILL_SCRIPT, Collections.emptyList(),
                voucherId.toString(), userId.toString(), String.valueOf(orderId));
        int r = result.intValue();
        if (r != 0) {
            return Result.fail(r == 1 ? "庫存不足" : "1個人只許下1單");
        }
        return Result.ok(orderId);
    }
//    @Override
//    public Result seckillVoucher(Long voucherId) {
//        Long userId = UserHolder.getUser().getId();
//        // 1. 執行lua腳本
//        Long result = stringRedisTemplate.execute(SECKILL_SCRIPT, Collections.emptyList(), voucherId.toString(), userId.toString());
//        int r = result.intValue();
//        if (r != 0) {
//            return Result.fail(r == 1 ? "庫存不足" : "1個人只許下1單");
//        }
//        // 2. 具有購買資格 把下單資訊保存到阻塞隊列
//        long orderId = redisIdWorker.nextId("order");
//        VoucherOrder voucherOrder = new VoucherOrder();
//        voucherOrder.setId(orderId);
//        voucherOrder.setUserId(userId);
//        voucherOrder.setVoucherId(voucherId);
//        orderTasks.add(voucherOrder);
//        return Result.ok(orderId);
//    }

//    @Override
//    public Result seckillVoucher(Long voucherId) {
//        // 1. 查詢優惠券
//        SeckillVoucher voucher = seckillVoucherService.getById(voucherId);
//        // 2. 秒殺是否開始
//        if (voucher.getBeginTime().isAfter(LocalDateTime.now())) {
//            return Result.fail("秒殺尚未開始");
//        }
//        // 3. 秒殺是否結束
//        if (voucher.getEndTime().isBefore(LocalDateTime.now())) {
//            return Result.fail("秒殺已經結束");
//        }
//        // 4. 庫存是否充足
//        if (voucher.getStock() < 1) {
//            return Result.fail("庫存不足");
//        }
//        Long userId = UserHolder.getUser().getId();
//        // 獲取鎖對象
////        SimpleRedisLock lock = new SimpleRedisLock("order:" + userId, stringRedisTemplate);
//        RLock lock = redissonClient.getLock("lock:order:" + userId);
//        boolean isLock = lock.tryLock(); // 重試的最大等待時間(-1) 超時釋放時間(30) 時間單位(s)
//        // 取鎖失敗
//        if (!isLock) {
//            return Result.fail("1個人只許下1單");
//        }
//        // 取鎖成功
//        try {
//            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
//            return proxy.createVoucherOrder(voucherId);
//        } finally {
//            // 釋放鎖
//            lock.unlock();
//        }
////        // 相同用戶上鎖
////        synchronized (userId.toString().intern()) {
////            // 這裡用的是this(VoucherOrderServiceImpl)對象 @Transactional會失效
////            // return createVoucherOrder(voucherId);
////
////            // Spring框架事務失效 1.AOP代理對象 2.synchronized鎖對象
////            // 本類的普通方法不能調用同類的事務方法 事務會失效 > 需要1個代理對象來調用
////
////            // 獲取代理對象(事務) > 確保@Transactional生效
////            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
////            return proxy.createVoucherOrder(voucherId);
////        }
//    }

    @Transactional
    public Result createVoucherOrder(Long voucherId) {
        // 5. 檢查1人1單
        Long userId = UserHolder.getUser().getId();
        int count = query().eq("user_id", userId).eq("voucher_id", voucherId).count();
        if (count > 0) {
            return Result.fail("用戶已購買");
        }
        // 6. 扣減庫存
        // 鎖的是相同用戶 不同用戶多線呈仍會進來 用樂觀鎖保護
        boolean success = seckillVoucherService.update()
                .setSql("stock = stock - 1")            // set stock = stock - 1
                .eq("voucher_id", voucherId)    // where id = ?
                // .eq("stock", voucher.getStock())     // and stock = ? (CAS 樂觀鎖) 成功率低
                .gt("stock", 0)             // and stock > 0 (CAS 樂觀鎖) 此題正解
                .update();
        if (!success) {
            return Result.fail("庫存不足");
        }
        // 7. 創建訂單
        long orderId = redisIdWorker.nextId("order");
        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setId(orderId);
        voucherOrder.setUserId(userId);
        voucherOrder.setVoucherId(voucherId);
        save(voucherOrder);
        // 7. 返回訂單id
        return Result.ok(orderId);
    }
}
