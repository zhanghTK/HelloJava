package tk.zhangh.java.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * Created by ZhangHao on 2016/10/14.
 */
public class ReadWriteLockTest {
    static class ReadWrite {
        private Object data = null;  // 共享数据，只能一个线程写数据，可以多个线程读数据
        private ReadWriteLock lock = new ReentrantReadWriteLock(false);  // 创建一个读写锁

        /**
         * 读数据
         */
        public void get() {
            // 加锁
            lock.readLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + " 准备读数据!");
                TimeUnit.MILLISECONDS.sleep((int)(Math.random()*1000));
                System.out.println(Thread.currentThread().getName() + "读出的数据为 :" + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }

        }

        /**
         * 写数据
         */
        public void put(Object data) {
            // 加锁
            lock.writeLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + " 准备写数据!");
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));
                this.data = data;
                System.out.println(Thread.currentThread().getName() + " 写入的数据: " + data);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReadWrite readWrite = new ReadWrite();
        ExecutorService service = Executors.newFixedThreadPool(5);
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            service.submit(new Thread() {
                @Override
                public void run() {
                    readWrite.get();
                }
            });
        }
        for (int i = 0; i < 3; i++) {
            service.submit(new Thread() {
                @Override
                public void run() {
                    readWrite.put(random.nextInt(8));
                }
            });
        }
    }
}
