package tk.zhangh.java.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试线程基本方法的使用
 * Created by ZhangHao on 2016/9/13.
 */
public class ThreadTest {

    /**
     * 中断基本用法
     */
    @Test
    public void test() throws Exception {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!interrupted()) {
                    System.out.println("run...");
                }
            }
        };
        thread.start();
        TimeUnit.MILLISECONDS.sleep(100);
        thread.interrupt();
    }

    /**
     * 中断与InterruptedException
     * @throws Exception
     */
    @Test
    public void testInterruptWithSleep() throws Exception {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("in run():will sleep 7S");
                try {
                    TimeUnit.SECONDS.sleep(7);
                } catch (InterruptedException e) {
                    System.out.println("in run():interrupted while sleep");
                    // 处理中断
                    Thread.currentThread().interrupt();
                    return;
                }
                System.out.println("in run():woke up");
            }
        };

        thread.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("in main:interrupting other thread");
        thread.interrupt();
    }

    /**
     * 线程让步测试
     * 测试结果不确定，yield仅是放弃线程控制权，根据OS,JVM有差异
     */
    @Test
    public void testYield() throws Exception{
        class YieldThread extends Thread {
            @Override
            public synchronized void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.printf("%s :%d\n", this.getName(), i);
                    // i整除4时，调用yield
                    if (i%4 == 0)
                        Thread.yield();
                }
            }
        }

        Thread thread0 = new YieldThread();
        Thread thread1 = new YieldThread();
        Thread thread2 = new YieldThread();
        thread0.start();
        thread1.start();
        thread2.start();
    }

    /**
     * 测试join
     * 让“主线程”等待“子线程”结束之后才能继续运行
     * @throws Exception
     */
    @Test
    public void testJoin() throws Exception {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " start.");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(getName() + " end.");
            }
        };
        thread.start();
        thread.join();
        System.out.println(Thread.currentThread().getName() + " end.");
    }

    /**
     * 测试线程运行中指定异常处理
     * @throws Exception
     */
    @Test
    public void testUncaughtExceptionHandler() throws Exception {

        class ExceptionHandler implements Thread.UncaughtExceptionHandler {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.printf("An exception has been capturedn ");
                System.out.printf("Thread: %sn", t.getId());
                System.out.printf("Exception: %s: %sn", e.getClass().getName(), e.getMessage());
                System.out.printf("Stack Trace: n");
                e.printStackTrace(System.out);
                System.out.printf("Thread status: %sn", t.getState());
            }
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());
                System.out.println(Integer.parseInt("123"));
                System.out.println(Integer.parseInt("XYZ")); //This will cause NumberFormatException
                System.out.println(Integer.parseInt("456"));
            }
        };
        thread.start();
        thread.join();
    }
}
