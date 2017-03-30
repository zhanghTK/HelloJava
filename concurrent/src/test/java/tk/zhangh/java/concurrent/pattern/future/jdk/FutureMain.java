package tk.zhangh.java.concurrent.pattern.future.jdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池搭配Future
 * Created by ZhangHao on 2017/3/30.
 */
public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> future = executor.submit(new RealData("name"));
        System.out.println("请求完毕");
        System.out.println(future.get());
    }
}
