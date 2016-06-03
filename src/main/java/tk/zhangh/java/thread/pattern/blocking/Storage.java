package tk.zhangh.java.thread.pattern.blocking;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZhangHao on 2016/6/3.
 */
public class Storage {
    private final int MAX_SIZE = 100;
    private LinkedBlockingQueue list = new LinkedBlockingQueue(100);

    /**
     *
     * @param num
     * @throws Exception
     */
    public void produce(int num) throws Exception{
        if (list.size() == MAX_SIZE) {
            System.out.println("【库存量】:" + MAX_SIZE + "/t暂时不能执行生产任务!");
        }

        for (int i = 1; i <= num; ++i) {
                list.put(new Object());
            System.out.println("【现仓储量为】:" + list.size());
        }
    }

    // 消费num个产品
    public void consume(int num) throws Exception{
        if (list.size() == 0) {
            System.out.println("【库存量】:0/t暂时不能执行生产任务!");
        }

        // 消费条件满足情况下，消费num个产品
        for (int i = 1; i <= num; ++i) {
                // 消费产品，自动阻塞
                list.take();
        }

        System.out.println("【现仓储量为】:" + list.size());
    }
}
