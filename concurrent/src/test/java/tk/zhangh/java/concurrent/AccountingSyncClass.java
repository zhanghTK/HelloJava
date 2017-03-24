package tk.zhangh.java.concurrent;

/**
 * 静态方法锁
 * Created by ZhangHao on 2017/3/25.
 */
public class AccountingSyncClass implements Runnable {
    private static int i = 0;
    private static synchronized void increase() {
        i++;
    }
    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new AccountingSyncClass());
        Thread thread2 = new Thread(new AccountingSyncClass());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(i);
    }
}
