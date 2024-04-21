package com.hmdp.utils;

public interface ILock {

    /**
     *
     * @param timeoutSec 鎖的過期時間
     * @return true(取鎖成功) false(取鎖失敗)
     */
    boolean tryLock(long timeoutSec);

    /**
     * 釋放鎖
     */
    void unLock();
}
