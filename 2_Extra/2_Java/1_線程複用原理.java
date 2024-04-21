package java05;

import java.util.concurrent.*;

public class Executor {

    static class BeimingFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("beiming_factory");
            thread.setUncaughtExceptionHandler((t, e) -> {
                System.out.println(t.getName() + ":::::::" + e.getMessage());
            });
            return thread;
        }
    }

    static class BeimingRejectionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 打印日誌 暫存任務 重新執行
            System.out.println("這個任務被拒絕了");
        }
    }

    public static void main(String[] args) {

        // corePoolSize
        // 線程的平均"工作"時間所占比的比例愈高 需要愈"少"的線程
        // 線程的平均"等待"時間所占比的比例愈高 需要愈"多"的線程

        /**
         * int corePoolSize             核心線程大小
         * int maximumPoolSize          線程池最大容量
         * long keepAliveTime           線程空閒時 線程存活的時間
         * TimeUnit unit                時間單位
         * ArrayBlockingQueue<Runnable> 任務對列
         * threadFactory                線程工廠
         * handler                      執行拒絕策略的對象
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1),
                new BeimingFactory(),
                new BeimingRejectionHandler()
        );

        executor.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("報錯了");
        });

        executor.execute(() -> {
            System.out.println("-");
        });

        executor.execute(() -> {
            System.out.println("這是一個超過對列大小的任務");
        });

        executor.shutdown();
    }

}