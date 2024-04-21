package com.itheima.multi_thread.ex3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 多線程要執行的任務
        MyCallable myCallable = new MyCallable();
        // 管理多線程運行的結果
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);

        // 線程對象
        Thread thread = new Thread(futureTask);
        // 啟動線程
        thread.start();

        // 獲取多線程運行的結果
        Integer result = futureTask.get();
        System.out.println(result);
    }

}
