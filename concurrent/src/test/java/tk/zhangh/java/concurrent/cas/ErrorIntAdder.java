package tk.zhangh.java.concurrent.cas;

/**
 * 没有使用锁，也没有确保CAS操作
 * 结果小于预期值
 * Created by ZhangHao on 2017/3/27.
 */
public class ErrorIntAdder {
    private static int count = 0;

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Adder());
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assert count != 100000;
        System.out.println(count);
    }

    private static class Adder implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                // 不加锁则异常
//                synchronized (ErrorIntAdder.class) {
                count++;
//                }
            }
        }
    }
}
