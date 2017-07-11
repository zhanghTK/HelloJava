package tk.zhangh.java.concurrent.thread;

/**
 * 实例方法锁
 * 锁方法的实例
 * Created by ZhangHao on 2017/3/25.
 */
public class SyncInstanceMethod implements Runnable {

    private static SyncInstanceMethod instance = new SyncInstanceMethod();

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(count);
    }

    private synchronized void increase() {
        count++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            increase();
        }
    }
}
