package tk.zhangh.java.concurrent.lock;

/**
 * 普通的synchronized加锁
 * Created by ZhangHao on 2017/3/28.
 */
public class SynchronizedLock implements Runnable {
    private static final Object lock = new Object();
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        SynchronizedLock synchronizedLock = new SynchronizedLock();
        Thread thread1 = new Thread(synchronizedLock);
        Thread thread2 = new Thread(synchronizedLock);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(count);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (lock) {
                synchronized (lock) {
                    count++;
                }
            }
        }
    }
}
