package tk.zhangh.java.concurrent.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用中断唤醒LockSupport暂停的线程
 * Created by ZhangHao on 2017/3/29.
 */
public class LockSupportDemo2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("线程将被暂停");
            LockSupport.park();
            System.out.println("线程被激活，Thread.currentThread().isInterrupted()：" + Thread.currentThread().isInterrupted());
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();  // Thread.currentThread().isInterrupted():true
//        LockSupport.unpark(thread);  // Thread.currentThread().isInterrupted():false
    }
}
