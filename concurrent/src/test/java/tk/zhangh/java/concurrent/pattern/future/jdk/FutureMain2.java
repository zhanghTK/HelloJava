package tk.zhangh.java.concurrent.pattern.future.jdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 线程池搭配Future
 * Created by ZhangHao on 2017/3/30.
 */
public class FutureMain2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> future = new FutureTask<>(new RealData("name"));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(future);
        System.out.println(future.get());
    }
}
