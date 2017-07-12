package tk.zhangh.java.concurrent.cas;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater Demo
 * 让普通变量享受原子操作
 * Created by ZhangHao on 2017/3/28.
 */
public class AtomicIntegerFieldUpdaterDemo {

    private final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");  // 指定类型，字段名
    private static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate stu = new Candidate(1, 0);
        Thread[] threads = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            threads[i] = new Thread(() -> {
                if (Math.random() > 0.4) {
                    scoreUpdater.incrementAndGet(stu);
                    allScore.incrementAndGet();
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < 10000; i++) {
            threads[i].join();
        }
        System.out.println("score=" + stu);
        System.out.println("allScore=" + allScore);
    }

    @AllArgsConstructor
    @Data
    private static class Candidate {
        int id;
        volatile int score;
    }
}
