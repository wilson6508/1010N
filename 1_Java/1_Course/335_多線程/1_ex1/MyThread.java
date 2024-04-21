package com.itheima.multi_thread.ex1;

public class MyThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName() + "_" + i);
        }
    }

}
