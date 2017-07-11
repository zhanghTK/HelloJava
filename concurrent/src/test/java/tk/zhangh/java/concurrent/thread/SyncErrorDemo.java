package tk.zhangh.java.concurrent.thread;

/**
 * 错误的实例方法锁
 * 加载不同的对象上
 * Created by ZhangHao on 2017/3/25.
 */
public class SyncErrorDemo implements Runnable {
    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new SyncErrorDemo());
        Thread thread2 = new Thread(new SyncErrorDemo());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(i);
    }

    private synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            increase();
        }
    }
}
