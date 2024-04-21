public class Executor {

    public static void main(String[] args) {
        // 底層以LinkedBlockingQueue裝任務對列 可能會累積大量請求導致JVM OOM
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        // SynchronousQueue不存任務->直接轉發任務 但是maximumPoolSize是int最大值->代表沒有限制線程數
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        // DelayedWorkQueue也是無界對列
        ExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    }

}