package tk.zhangh.java.concurrent;

/**
 * join本质：
 * while(isAlive()) {
 *     wait(0);  // 当前线程等待在调用wait操作的地方
 * }
 * Created by ZhangHao on 2017/3/24.
 */
public class JoinDemo {
    private volatile static long count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (++count < 1_000_000_000) {
            }
        });
        thread.start();
        thread.join();
        System.out.println(count);
    }
}
