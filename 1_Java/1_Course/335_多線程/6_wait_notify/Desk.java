package com.itheima.multi_thread.wait_notify;

public class Desk {

    // 無麵條0 有麵條1
    public static int foodFlag = 0;

    // 總數
    public static int count = 10;

    // 鎖對象
    public static Object lock = new Object();

}
