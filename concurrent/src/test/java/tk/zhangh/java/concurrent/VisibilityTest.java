package tk.zhangh.java.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 可见性问题示例
 * -server模式下永远无法停下来
 * JVM优化只在循环开始时检测stop变量
 * Created by ZhangHao on 2017/3/27.
 */
public class VisibilityTest extends Thread {

    //    private volatile boolean stop;
    private boolean isStop;

    public static void main(String[] args) throws Exception {
        VisibilityTest visibilityTest = new VisibilityTest();
        visibilityTest.start();
        TimeUnit.SECONDS.sleep(1);
        visibilityTest.isStop = true;
        TimeUnit.SECONDS.sleep(2);
    }

    @Override
    public void run() {
        int i = 0;
        while (!isStop) {
            i++;
        }
        System.out.println(i);
    }
}
