package tk.zhangh.java.thread.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
/**
 * Future Demo
 * Created by ZhangHao on 2016/9/12.
 */
public class FutureTest {
    static class Slave implements Callable<String> {
        @Override
        public String call() throws Exception {
            TimeUnit.SECONDS.sleep(5);
            return "Slave finished work";
        }
    }

    public static void main(String[] args) throws Exception{
        FutureTask<String> futureTask = new FutureTask<>(new Slave());
        Executors.newCachedThreadPool().submit(futureTask);
        System.out.println("Master order Slave do work");
        doSomeThing();
        System.out.print("Master check Slave's work:");
        while (!futureTask.isDone()) {
            System.out.println("not finished,Master waiting");
            System.out.print("Master check Slave's work:");
            TimeUnit.MILLISECONDS.sleep(500);
        }
        System.out.println("finished");
        System.out.println("Master get massage:" + futureTask.get());
    }

    private static void doSomeThing() throws Exception {
        for (int i = 0; i < 2; i++) {
            System.out.println("Master do something...");
            TimeUnit.MILLISECONDS.sleep(1300);
        }
    }
}
