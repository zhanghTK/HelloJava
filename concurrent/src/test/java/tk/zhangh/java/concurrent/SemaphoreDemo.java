package tk.zhangh.java.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Created by ZhangHao on 2017/3/23.
 */
@Slf4j
public class SemaphoreDemo {
    private static final int WORKER_NUMBER = 10;
    private static final int RESOURCE_NUM = 3;
    private static final boolean isFair = true;

    public static void main(String[] args) throws Exception {
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