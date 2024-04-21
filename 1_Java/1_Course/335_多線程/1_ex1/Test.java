package com.itheima.multi_thread.ex1;

public class Test {

    public static void main(String[] args) {
        MyThread m1 = new MyThread();
        m1.setName("m1");
        m1.start();

        MyThread m2 = new MyThread();
        m2.setName("m2");
        m2.start();
    }

}
