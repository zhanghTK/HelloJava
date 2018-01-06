package tk.zhangh.java.concurrent.thread;

/**
 * 设置线程优先级
 * 不建议使用，依赖OS实现
 * Created by ZhangHao on 2017/3/24.
 */
public class PriorityDemo {

    public static void main(String[] args) {
        Thread high = new ThreadPriority("high");
        high.setPriority(Thread.MAX_PRIORITY);  // 设置线程高优先级
        Thread low = new ThreadPriority("low");
        low.setPriority(Thread.MIN_PRIORITY);  // 设置线程低优先级
        high.start();
        low.start();
    }

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
}
