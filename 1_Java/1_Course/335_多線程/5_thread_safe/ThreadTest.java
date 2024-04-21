package com.itheima.multi_thread.thread_safe;

public class ThreadTest {

    public static void main(String[] args) {
        MyThread m1 = new MyThread();
        MyThread m2 = new MyThread();
        MyThread m3 = new MyThread();

        m1.setName("窗口1");
        m2.setName("窗口2");
        m3.setName("窗口3");

        m1.start();
        m2.start();
        m3.start();
    }

}
