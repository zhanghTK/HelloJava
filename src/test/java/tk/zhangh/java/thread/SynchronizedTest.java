package tk.zhangh.java.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * synchronized(this)以及非静态的synchronized方法只能防止多个线程同时执行同一个对象的同步代码段
 * 对于非staic的synchronized方法，锁的就是对象本身也就是this
 * synchronized(Sync.class)实现了全局锁的效果，锁代码段
 * static synchronized方法也相当于全局锁，锁代码段
 * Created by ZhangHao on 2016/10/14.
 */
public class SynchronizedTest {

    /**
     * 加锁例子，分别对方法，对象加锁
     */
    class Sync {
        synchronized void testSynMethod() {
            System.out.println("start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end");
        }

        void testSyncObj() {
            synchronized (this) {
                System.out.println("start");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end");
            }
        }
    }

    /**
     * 错误使用锁，锁方法
     */
    @Test
    public void testErrorSynchronizedMethod() throws Exception {
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    Sync sync = new Sync();
                    sync.testSynMethod();
                }
            }.start();
        }
        TimeUnit.SECONDS.sleep(4);
    }

    /**
     * 错误使用锁，锁对象
     */
    @Test
    public void testErrorSynchronizedObj() throws Exception {
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    Sync sync = new Sync();
                    sync.testSyncObj();
                }
            }.start();
        }
        TimeUnit.SECONDS.sleep(4);
    }

    /**
     * 正确的使用锁
     */
    @Test
    public void testSynchronized() throws Exception {
        class MyThread extends Thread {
            private Sync sync;

            public MyThread(Sync sync) {
                this.sync = sync;
            }

            @Override
            public void run() {
                sync.testSyncObj();
            }
        }
        Sync sync = new Sync();
        for (int i = 0; i < 3; i++) {
            Thread thread = new MyThread(sync);
            thread.start();
        }
        TimeUnit.SECONDS.sleep(4);
    }

    /**
     * 正确使用锁，锁Class对象
     */
    @Test
    public void testSynchronizedClass() throws Exception {
        class SyncClass {
            public void test() {
                synchronized (SyncClass.class) {
                    System.out.println("start");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end");
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    SyncClass sync = new SyncClass();
                    sync.test();
                }
            }.start();
        }
        TimeUnit.SECONDS.sleep(4);
    }

    /**
     * 测试锁方法造成的缺陷
     */
    @Test
    public void testSynchronizedMethod() throws Exception {
        class Service {
            synchronized void fun1() throws Exception {
                System.out.println("fun1 start");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("fun1 end");
            }

            synchronized void fun2() throws Exception {
                System.out.println("fun2 start");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("fun2 end");
            }
        }

        class MyThread1 extends Thread {
            Service service;

            public MyThread1(Service service) {
                this.service = service;
            }

            @Override
            public void run() {
                try {
                    service.fun1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        class MyThread2 extends Thread {
            Service service;

            public MyThread2(Service service) {
                this.service = service;
            }

            @Override
            public void run() {
                try {
                    service.fun2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Service service = new Service();
        Thread thread1 = new MyThread1(service);
        Thread thread2 = new MyThread2(service);
        thread1.run();
        thread2.run();
        TimeUnit.SECONDS.sleep(50);
    }
}
