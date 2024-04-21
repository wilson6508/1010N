package com.itheima.multi_thread.thread_safe;

public class MyThread extends Thread {

    static int ticket = 0;
    static Object obj = new Object(); // 鎖對象 要唯一

    @Override
    public void run() {
        while (true) {
            // synchronized (obj) {
            synchronized (MyThread.class) {
                if (ticket < 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    ticket++;
                    System.out.println(getName() + "_賣第" + ticket + "張票");
                } else {
                    break;
                }
            }
        }
    }

}
