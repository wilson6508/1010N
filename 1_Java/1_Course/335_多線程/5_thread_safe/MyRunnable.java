package com.itheima.multi_thread.thread_safe;

public class MyRunnable implements Runnable {

    // 只會創建一次所以可以不用static
    int ticket = 0;

    @Override
    public void run() {
        while (true) {
            if (method()) break;
        }
    }

    // 靜態方法 鎖對象是當前類的字節碼對象(唯一)
    // 普通方法 鎖對象是this
    private synchronized boolean method() {
        if (ticket == 100) {
            return true;
        } else {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ticket++;
            System.out.println(Thread.currentThread().getName() + "_賣第" + ticket + "張票");
        }
        return false;
    }

}
