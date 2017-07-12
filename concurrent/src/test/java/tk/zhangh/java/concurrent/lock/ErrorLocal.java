package tk.zhangh.java.concurrent.lock;

/**
 * 错误加锁
 * 对包装类加锁
 * 包装类型会进行自动拆箱作为基本类型
 * Created by ZhangHao on 2017/4/5.
 */
public class ErrorLocal {
    static Integer i = 0;

    public static void main(String[] args) throws InterruptedException {
        AddThread t1 = new AddThread();
        AddThread t2 = new AddThread();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }

    private static class AddThread extends Thread {
        public void run() {
            for (int k = 0; k < 100000; k++) {
                synchronized (i) {
                    i++;
                }
            }
        }
    }
}
