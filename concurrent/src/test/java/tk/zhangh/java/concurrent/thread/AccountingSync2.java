package tk.zhangh.java.concurrent.thread;

/**
 * 实例方法锁
 * Created by ZhangHao on 2017/3/25.
 */
public class AccountingSync2 implements Runnable {

    private static AccountingSync2 instance = new AccountingSync2();

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
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
