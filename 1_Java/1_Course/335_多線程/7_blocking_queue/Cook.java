import java.util.concurrent.ArrayBlockingQueue;

public class Cook extends Thread {

    ArrayBlockingQueue<String> queue;

    public Cook(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.put("麵條_");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}