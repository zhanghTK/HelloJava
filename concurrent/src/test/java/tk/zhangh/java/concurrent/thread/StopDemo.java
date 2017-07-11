package tk.zhangh.java.concurrent.thread;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Stop线程Demo
 * 终止线程，不推荐使用，直接结束线程会造成状态不一致、抛出ThreadDeath
 * Created by ZhangHao on 2017/3/27.
 */
public class StopDemo {
    private static final int[] array = new int[80_000_000];

    static {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(i + 1);
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            try {
                Arrays.sort(array);
                System.out.println("sort end");
            } catch (Error err) {
                err.printStackTrace();
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        t.stop();
    }
}
