package com.itheima.multi_thread.thread_method;

public class AppleThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName() + "_" + i);
        }
    }

}
