package tk.zhangh.java.thread.pattern.now;


/**
 * Created by ZhangHao on 2016/6/3.
 */
public class Consumer implements Runnable{
    private int num;
    private Storage storage;

    public Consumer(Storage storage, int num) {
        this.num = num;
        this.storage = storage;
    }

    public void run() {
        consume(num);
    }

    public void consume(int num){
        try {
            storage.consume(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
