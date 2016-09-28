package tk.zhangh.java.thread;


import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试使用lock，condition实现阻塞模拟生产者消费者模型
 * Created by ZhangHao on 2016/9/28.
 */
public class ConditionTest {
    private static final int QUEUE_SIZE = 10;
    private static PriorityQueue<Integer> queue = new PriorityQueue<>(QUEUE_SIZE);
    private static Lock lock = new ReentrantLock();  // 锁对象
    private static Condition notFull = lock.newCondition();  // 生产条件
    private static Condition notEmpty = lock.newCondition();  // 消费条件

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
                lock.lock();
                try {
                    // 队列空
                    while(queue.size() == 0){
                        try {
                            // 消费者线程休眠
                            System.out.println("队列空，等待数据");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();  // 取出
                    notFull.signal();  // 唤醒生产者线程
                    System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                } finally{
                    // 释放锁
                    lock.unlock();
                }
            }
        }
    }

    private static class Producer extends Thread{

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while(true){
                // 加锁
                lock.lock();
                try {
                    // 队列满
                    while(queue.size() == QUEUE_SIZE){
                        try {
                            // 生产者线程休眠
                            System.out.println("队列满，等待有空余空间");
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);  // 插入
                    notEmpty.signal();  // 唤醒生产者线程
                    System.out.println("向队列取中插入一个元素，队列剩余"+queue.size()+"个元素");
                } finally{
                    // 释放锁
                    lock.unlock();
                }
            }
        }
    }
}
