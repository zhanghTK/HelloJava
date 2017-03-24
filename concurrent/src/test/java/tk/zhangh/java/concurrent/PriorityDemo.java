package tk.zhangh.java.concurrent;

/**
 * 设置线程优先级
 * Created by ZhangHao on 2017/3/24.
 */
public class PriorityDemo {

    private static class ThreadPriority extends Thread {
        private static int count = 0;

        ThreadPriority(String name) {
            setName(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 1_000) {
                        System.out.println(getName() + " is complete");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread high = new ThreadPriority("high");
        Thread low = new ThreadPriority("low");
        low.setPriority(Thread.MIN_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);
        high.start();
        low.start();
    }
}
