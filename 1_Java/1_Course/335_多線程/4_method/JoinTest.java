package com.itheima.multi_thread.thread_method;

public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        AppleThread appleThread = new AppleThread();
        appleThread.setName("apple");
        appleThread.start();

        // 將apple線程插入到當前線程(main)之前 或者可以解釋為等待appleThread完成
        appleThread.join();

        for (int i = 0; i < 10; i++) {
            System.out.println("main_" + i);
        }
    }

}
