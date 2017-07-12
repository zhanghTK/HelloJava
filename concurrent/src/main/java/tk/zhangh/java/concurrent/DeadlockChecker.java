package tk.zhangh.java.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * 检测死锁中断
 * Created by ZhangHao on 2017/3/28.
 */
public class DeadlockChecker {
    private static final ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
    final static Runnable deadlockCheck = () -> {
        while (true) {
            long[] deadlockedThreadIds = mbean.findDeadlockedThreads();
            if (deadlockedThreadIds != null) {
                ThreadInfo[] threadInfos = mbean.getThreadInfo(deadlockedThreadIds);
                for (Thread thread : Thread.getAllStackTraces().keySet()) {
                    for (int i = 0; i < threadInfos.length; i++) {
                        if (thread.getId() == threadInfos[i].getThreadId()) {
                            thread.interrupt();
                        }
                    }
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static void check() {
        Thread thread = new Thread(deadlockCheck);
        thread.setDaemon(true);
        thread.start();
    }
}
