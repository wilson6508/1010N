package com.hmdp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.hmdp.utils.RedisIdWorker;
import com.hmdp.utils.UserHolder;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {

    @Resource
    private ISeckillVoucherService seckillVoucherService;
    @Resource
    private RedisIdWorker redisIdWorker;

    @Override
    public Result seckillVoucher(Long voucherId) {
        // 1. 查詢優惠券
        SeckillVoucher voucher = seckillVoucherService.getById(voucherId);
        // 2. 秒殺是否開始
        if (voucher.getBeginTime().isAfter(LocalDateTime.now())) {
            return Result.fail("秒殺尚未開始");
        }
        // 3. 秒殺是否結束
        if (voucher.getEndTime().isBefore(LocalDateTime.now())) {
            return Result.fail("秒殺已經結束");
        }
        // 4. 庫存是否充足
        if (voucher.getStock() < 1) {
            return Result.fail("庫存不足");
        }
        Long userId = UserHolder.getUser().getId();
        // 相同用戶上鎖
        synchronized (userId.toString().intern()) {
            // 這裡用的是this(VoucherOrderServiceImpl)對象 @Transactional會失效
            // return createVoucherOrder(voucherId);

            // Spring框架事務失效 1.AOP代理對象 2.synchronized鎖對象
            // 本類的普通方法不能調用同類的事務方法 事務會失效 > 需要1個代理對象來調用

            // 獲取代理對象(事務) > 確保@Transactional生效
            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            return proxy.createVoucherOrder(voucherId);
        }
    }

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
