package tk.zhangh.java.thread.pattern.now;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZhangHao on 2016/6/3.
 */
public class Storage {
    private final int MAX_SIZE = 100;
    private LinkedList list = new LinkedList();
    private final Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();

    /**
     * 生产产品
     * @param num 生产产品数量
     * @throws Exception
     */
    public void produce(int num)throws Exception{
        lock.lock();
        System.out.println("【准备生产" + num + "个】");
        while (list.size() + num > MAX_SIZE){
            System.out.println("现有空间" + (MAX_SIZE - list.size()) + "。空间不足，暂停生产" );
            full.await();
        }
        System.out.println("开始生产...");
        for (int i = 0; i < num; i++) {
            Thread.sleep(1000);
            list.add(new Object());
        }
        System.out.println("已生产" + num + "个产品，现有产品：" + list.size());
        full.signalAll();
        empty.signalAll();
        lock.unlock();
    }

    /**
     * 消费产品
     * @param num 产品数量
     * @throws Exception
     */
    public void consume(int num)throws Exception{
        lock.lock();
        System.out.println("【准备消费" + num + "个】");
        while (list.size() < num){
            System.out.println("现有产品" + list.size() + "。库存不足，暂停消费");
            empty.wait();
        }
        System.out.println("开始消费...");
        for (int i = 0; i < num; i++) {
            Thread.sleep(1000);
            list.remove();
        }
        System.out.println("已消费" + num + "个产品，现有产品：" + list.size());
        full.signalAll();
        empty.signalAll();
        lock.unlock();
    }
}
