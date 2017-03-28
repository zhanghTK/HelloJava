package tk.zhangh.java.concurrent.cas;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * AtomicIntegerArray Demo
 * Created by ZhangHao on 2017/3/28.
 */
public class AtomicIntegerArrayDemo {
    private static AtomicIntegerArray array = new AtomicIntegerArray(10);
    private static Thread[] threads = new Thread[10];

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    array.getAndIncrement(j % array.length());
                }
            });
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        System.out.println(array);
    }
}
