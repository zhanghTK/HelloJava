package tk.zhangh.java.thread;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/**
 * 测试使用wait,notify进行轮询与休眠实现阻塞模拟生产者消费者模型
 * Created by ZhangHao on 2016/9/28.
 */
public class WaitNotifyTest {
    private static final int QUEUE_SIZE = 10;
    private static PriorityQueue<Integer> queue = new PriorityQueue<>(QUEUE_SIZE);

    public static void main(String[] args) throws Exception {
        new Producer().start();
        new Consumer().start();
        TimeUnit.MILLISECONDS.sleep(50);
        System.exit(0);
    }

    /**
     * 消费者
     */
    private static class Consumer extends Thread{
        @Override
        public void run() {
            consume();
        }
        private void consume() {
            while(true){
                // 加锁
                synchronized (queue) {
                    // 队列空
                    while(queue.size() == 0){
                        try {
                            // 休眠，释放锁
                            System.out.println("队列空，等待数据");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    queue.poll();  // 取出
                    queue.notify();  // 唤醒
                    System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                }
            }
        }
    }

    /**
     * 生产者
     */
    private static class Producer extends Thread{
        @Override
        public void run() {
            produce();
        }
        private void produce() {
            while(true){
                // 加锁
                synchronized (queue) {
                    // 队列满
                    while(queue.size() == QUEUE_SIZE){
                        try {
                            // 休眠，释放锁
                            System.out.println("队列满，等待有空余空间");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    queue.offer(1);  // 取出
                    queue.notify();  // 唤醒
                    System.out.println("向队列取中插入一个元素，队列剩余"+queue.size()+"个元素");
                }
            }
        }
    }
}
