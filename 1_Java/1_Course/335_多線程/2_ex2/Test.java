package com.itheima.multi_thread.ex2;

public class Test {

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();

        Thread t1 = new Thread(myRunnable);
        t1.setName("t1");
        t1.start();

        Thread t2 = new Thread(myRunnable);
        t2.setName("t2");
        t2.start();
    }

}
