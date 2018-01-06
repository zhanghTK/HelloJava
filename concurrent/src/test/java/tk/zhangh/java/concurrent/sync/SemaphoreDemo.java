package tk.zhangh.java.concurrent.sync;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Semaphore使用
 * Semaphore是线程间同时访问的临界区资源
 * 申请的资源使用后需要手动释放
 * 例子：10个线程访问3个资源
 * Created by ZhangHao on 2017/3/23.
 */
@Slf4j
public class SemaphoreDemo {
    private static final int WORKER_NUMBER = 10;
    private static final int RESOURCE_NUM = 3;
    private static final boolean isFair = true;

    public static void main(String[] args) throws Exception {
        // 初始化资源个数，资源访问的公平性
        Semaphore semaphore = new Semaphore(RESOURCE_NUM, isFair);
        Stream.iterate(0, item -> item + 1)
                .limit(WORKER_NUMBER)
                .forEach(i -> new Thread(new Worker(i, semaphore)).start());
    }

    @Data
    @AllArgsConstructor
    static class Worker implements Runnable {
        private int name;
        private Semaphore semaphore;

        @Override
        public void run() {
            try {
                log.info("{} wait resource", name);
                semaphore.acquire();
                log.info("{} get resource", name);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
            log.info("{} release resource", name);
        }
    }
}