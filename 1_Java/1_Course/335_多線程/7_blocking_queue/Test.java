package com.itheima.multi_thread.wait_notify_2;

import java.util.concurrent.ArrayBlockingQueue;

public class Test {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        Cook c = new Cook(queue);
        c.start();
        Foodie f = new Foodie(queue);
        f.start();
    }

}
