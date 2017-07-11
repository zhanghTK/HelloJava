package tk.zhangh.java.concurrent.thread;

/**
 * 对象锁
 * 锁对象
 * Created by ZhangHao on 2017/3/24.
 */
public class SyncInstance implements Runnable {

    private static SyncInstance instance = new SyncInstance();

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

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (instance) {
                count++;
            }
        }
    }
}
