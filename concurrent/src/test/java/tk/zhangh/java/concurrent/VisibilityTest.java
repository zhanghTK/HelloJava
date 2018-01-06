package tk.zhangh.java.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 可见性问题示例
 * -server模式下永远无法停下来(JDK版本也有关系)
 * JVM优化只在循环开始时检测stop变量
 * Created by ZhangHao on 2017/3/27.
 */
public class VisibilityTest extends Thread {


    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Runner());
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        Runner.isStop = true;
    }

    static class Runner implements Runnable {

//            private static volatile boolean isStop;
        private static boolean isStop;

        @Override
        public void run() {
            int i = 0;
            while (!isStop) {
                i++;
            }
            System.out.println(i);
        }
    }
}
