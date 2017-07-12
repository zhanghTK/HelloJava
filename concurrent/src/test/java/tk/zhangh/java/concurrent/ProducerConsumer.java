package tk.zhangh.java.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 生产者消费者模型
 * Created by ZhangHao on 2017/3/30.
 */
public class ProducerConsumer {
    private static BlockingQueue<PCData> queue = new LinkedBlockingDeque<>();

    public static void main(String[] args) {
//        new Thread(new Consumer()).start();
//        new Thread(new Producer()).start();
//        new Thread(new Producer()).start();
        ExecutorService service = Executors.newFixedThreadPool(11);
        for (int i = 0; i < 10; i++) {
            service.submit(new Consumer());
        }
        service.submit(new Producer());
    }

    @Data
    @AllArgsConstructor
    static class PCData {
        int data;
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!queue.offer(new PCData(new Random(System.currentTimeMillis()).nextInt()))) {
                    System.out.println("\noffer error, queue size:" + queue.size());
                } else {
                    System.out.println("\noff success, queue size:" + queue.size());
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                while (true) {
                    try {
                        PCData data = queue.take();
                        System.out.println("\n" + data.getData() + " * " + data.getData() + " = " + data.getData() * data.getData());
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
