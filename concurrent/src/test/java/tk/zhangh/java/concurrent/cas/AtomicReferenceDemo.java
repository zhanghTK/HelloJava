package tk.zhangh.java.concurrent.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference Demo
 * Created by ZhangHao on 2017/3/27.
 */
public class AtomicReferenceDemo {
    private static final AtomicReference<String> atomicStr = new AtomicReference<>("abc");

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(((int) (Math.random() * 100)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String print = "Thread-" + Thread.currentThread().getId() + " ";
                print += atomicStr.compareAndSet("abc", "def") ? "change value" : "change failed";
                System.out.println(print);
            }).start();
        }
    }
}
