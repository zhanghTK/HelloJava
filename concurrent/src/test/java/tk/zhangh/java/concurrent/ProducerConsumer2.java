package tk.zhangh.java.concurrent;

/**
 * Created by ZhangHao on 2017/7/13.
 */
public class ProducerConsumer2 {
    public static void main(String[] args) {
        QueueBuffer queueBuffer = new QueueBuffer();
        new Producer(queueBuffer);
        new Consumer(queueBuffer);
    }

    static class QueueBuffer {
        int n;
        boolean valueSet = false;

        synchronized int get() {
            if (!valueSet)
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                }
            System.out.println("Got: " + n);
            valueSet = false;
            notify();
            return n;
        }

        synchronized void put(int n) {
            if (valueSet)
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                }
            this.n = n;
            valueSet = true;
            System.out.println("Put: " + n);
            notify();
        }
    }

    static class Producer implements Runnable {
        private QueueBuffer queueBuffer;

        Producer(QueueBuffer queueBuffer) {
            this.queueBuffer = queueBuffer;
            new Thread(this, "Producer").start();
        }

        @Override
        public void run() {
            int i = 0;
            while (true) {
                queueBuffer.put(i++);
            }
        }
    }

    static class Consumer implements Runnable {
        private QueueBuffer queueBuffer;

        Consumer(QueueBuffer queueBuffer) {
            this.queueBuffer = queueBuffer;
            new Thread(this, "Consumer").start();
        }

        public void run() {
            while (true) {
                queueBuffer.get();
            }
        }
    }
}
