package tk.zhangh.java.thread.pattern.old;

import java.util.LinkedList;

/**
 * Created by ZhangHao on 2016/6/3.
 */
public class Storage {
    private final int MAX_SIZE = 2;
    private LinkedList list = new LinkedList();

    /**
     * 生产产品
     */
    public void product(int num)throws Exception{
        synchronized (list){
            while (list.size() + num > MAX_SIZE){
                System.out.println("准备生产" + num + "个产品，现有空间" + (MAX_SIZE - list.size()) + "。空间不足，暂停生产");
                list.wait();
            }
            System.out.println("开始生产...");
            for (int i = 0; i <num ; i++) {
                Thread.sleep(100);
                list.add(new Object());
            }
            System.out.println("已生产" + num + "个产品，现有产品：" + list.size());
            list.notifyAll();
        }
    }

    public void consume(int num)throws Exception{
        synchronized (list){
            while (list.size() < num){
                System.out.println("准备消费" + num + "个产品，现有产品" + list.size() + "。库存不足，暂停消费");
                list.wait();
            }
            System.out.println("开始消费...");
            for (int i = 0; i < num; i++) {
                list.remove();
            }
            System.out.println("已消费" + num + "个产品，现有产品：" + list.size());
            list.notifyAll();
        }
    }
}
