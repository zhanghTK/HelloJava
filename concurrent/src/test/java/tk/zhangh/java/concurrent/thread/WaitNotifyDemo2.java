package tk.zhangh.java.concurrent.thread;

/**
 * 使用wait，notify多线程交替输出1,2,1,2...
 * 两个操作前都要加锁，wait后会释放锁，保证有序
 * Created by ZhangHao on 2017/7/13.
 */
public class WaitNotifyDemo2 {
    private static Object lock = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Output(1));
        Thread thread2 = new Thread(new Output(2));

        thread1.start();
        thread2.start();
    }

    static class Output implements Runnable {
        private int num;

        Output(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(num);
                }
            }
        }
    }
}
