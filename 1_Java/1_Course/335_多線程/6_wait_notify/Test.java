package com.itheima.multi_thread.wait_notify;

public class Test {

    public static void main(String[] args) {
        Cook c = new Cook();
        c.setName("廚師");
        c.start();

        Foodie f = new Foodie();
        f.setName("吃貨");
        f.start();
    }

}
