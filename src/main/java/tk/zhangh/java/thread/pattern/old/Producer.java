package tk.zhangh.java.thread.pattern.old;

/**
 * Created by ZhangHao on 2016/6/3.
 */
public class Producer implements Runnable{
    private Storage storage;
    private int num;

    public Producer(Storage storage, int num) {
        this.storage = storage;
        this.num = num;
    }

    public void run() {
        produce(num);
    }

    public void produce(int num){
        try {
            storage.product(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
