import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public static void main(String[] args) throws InterruptedException {
    // 獲取線程池對象
    ExecutorService executorService = Executors.newCachedThreadPool(); // 數量上限為Integer.MAX_VALUE
    // 提交任務
    executorService.submit(new MyRunnable());
    Thread.sleep(1000);
    executorService.submit(new MyRunnable());
    // 銷毀線程池
    executorService.shutdown();
}