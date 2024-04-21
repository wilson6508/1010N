package com.itheima.multi_thread.wait_notify;

public class Foodie extends Thread {

    @Override
    public void run() {
        while (true) {
            synchronized (Desk.lock) {
                if (Desk.count == 0) {
                    break;
                } else {
                    // 無麵條 -> 等待
                    if (Desk.foodFlag == 0) {
                        try {
                            Desk.lock.wait(); // 當前線程跟鎖進行綁定
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else { // 有麵條 -> 開吃
                        Desk.count--;
                        System.out.println("吃貨正在吃 還能再吃" + Desk.count + "碗");
                        Desk.foodFlag = 0;
                        Desk.lock.notifyAll(); // 喚醒綁定在此鎖上的 所有線程
                    }
                }
            }
        }
    }

}
