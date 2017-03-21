package tk.zhangh.java.practice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Map性能测试
 * Created by ZhangHao on 2017/3/18.
 */
public class CrunchifyConcurrentHashMapVsSynchronizedMap {
    private static final int THREAD_POOL_SIZE = 5;

    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> crunchifyHashTableObject = new Hashtable<>();
        crunchifyPerformTest(crunchifyHashTableObject);

        Map<String, Integer> crunchifySynchronizedMapObject = Collections.synchronizedMap(new HashMap<String, Integer>());
        crunchifyPerformTest(crunchifySynchronizedMapObject);

        Map<String, Integer> crunchifyConcurrentHashMapObject = new ConcurrentHashMap<>();
        crunchifyPerformTest(crunchifyConcurrentHashMapObject);
    }

    private static void crunchifyPerformTest(final Map<String, Integer> crunchifyThreads) throws InterruptedException {
        System.out.println("Test started for: " + crunchifyThreads.getClass());
        long averageTime = 0;
        for (int i = 0; i < 15; i++) {
            long totalTime = countOnceTime(crunchifyThreads);
            averageTime += totalTime;
            System.out.println("2500K entried added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + crunchifyThreads.getClass() + " the average time is " + averageTime / 5 + " ms\n");
    }

    private static long countOnceTime(Map<String, Integer> crunchifyThreads) throws InterruptedException {
        long startTime = System.nanoTime();
        ExecutorService crunchifyExServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        Stream.iterate(0, item -> item + 1).limit(THREAD_POOL_SIZE).forEachOrdered(i -> crunchifyExServer.execute(() ->
                Stream.iterate(0, item -> item + 1).limit(500000).forEachOrdered(j -> {
                    Integer crunchifyRandomNumber = (int) Math.ceil(Math.random() * 550000);
                    crunchifyThreads.get(String.valueOf(crunchifyRandomNumber));
                    crunchifyThreads.put(String.valueOf(crunchifyRandomNumber), crunchifyRandomNumber);
                })));
        crunchifyExServer.shutdown();
        crunchifyExServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        return (System.nanoTime() - startTime) / 1000000L;
    }
}
