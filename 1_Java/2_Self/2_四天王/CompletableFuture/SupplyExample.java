package test07;

import java.util.concurrent.*;

public class SupplyExample {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = myThreadPool();
        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            if (name.contains("2")) {
                try {
                    Thread.sleep(5000); // 模擬查詢超時
                } catch (InterruptedException e) {
                    System.out.println(5566);
                }
            }
            System.out.println(name);
        };
        for (int i = 0; i < 3; i++) {
            executor.submit(task);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                System.out.println("time out");
                return;
            }
            System.out.println("All tasks have completed.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted.");
        }
        System.out.println(7788);
    }

    public static ThreadPoolExecutor myThreadPool() {
        int coreSize = 6;
        int maxSize = 12;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        long keepAliveTime = 1L;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(maxSize);
        return new ThreadPoolExecutor(coreSize, maxSize, keepAliveTime, timeUnit, queue, new ThreadPoolExecutor.CallerRunsPolicy());
    }

}

// ExecutorService 等待線程完成
// https://www.twle.cn/t/373
// Java 中的異步編程工具 CompletableFuture
// http://www.mydlq.club/article/124/