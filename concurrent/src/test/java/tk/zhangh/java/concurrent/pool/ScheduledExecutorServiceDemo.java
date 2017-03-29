package tk.zhangh.java.concurrent.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务线程管理 Demo
 * Created by ZhangHao on 2017/3/29.
 */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        // 如果前面的任务没有完成，则会跳过本次计划任务
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(System.currentTimeMillis() / 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
