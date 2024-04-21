package com.itheima.multi_thread.thread_method;

public class NameTest {

    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        System.out.println("thread.getName() = " + thread.getName());
        /*
            當JVM虛擬機啟動之後 會開啟多條線程
            其中一條是main main線程會調用main方法
         */
    }

}
