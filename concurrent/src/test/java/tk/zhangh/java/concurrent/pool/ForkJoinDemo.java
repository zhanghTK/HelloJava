package tk.zhangh.java.concurrent.pool;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork Join示例
 * Created by ZhangHao on 2017/3/29.
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10000;
    private long start;
    private long end;

    ForkJoinDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo countTask = new ForkJoinDemo(0, 1000);
        ForkJoinTask<Long> result = forkJoinPool.submit(countTask);
        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCompute = (end - start) < THRESHOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            long step = (start + end) / 100;
            ArrayList<ForkJoinDemo> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                ForkJoinDemo subTask = new ForkJoinDemo(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (ForkJoinDemo task : subTasks) {
                sum += task.join();
            }
        }
        return sum;
    }
}
