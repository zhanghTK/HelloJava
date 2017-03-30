package tk.zhangh.java.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 生产者消费者模型
 * Created by ZhangHao on 2017/3/30.
 */
public class ProducerConsumer {
    private static BlockingQueue<PCData> queue = new LinkedBlockingDeque<>();

    public static void main(String[] args) {
        new Thread(new Consumer()).start();
        new Thread(new Producer()).start();
        new Thread(new Producer()).start();
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
                if (!queue.offer(new PCData(1))) {
                    System.out.println("offer error");
                } else {
                    System.out.println("off success");
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
                        System.out.println(data.getData() + " * " + data.getData() + " = " + data.getData() * data.getData());
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
