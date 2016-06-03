package tk.zhangh.java.thread.pattern.blocking;

/**
 * Created by ZhangHao on 2016/6/3.
 */
public class App {
    public static void main(String[] args) {
        Storage repertory = new Storage();

        Thread[] producerThreads = new Thread[20];
        for (int i = 0; i < producerThreads.length; i++) {
            producerThreads[i] = new Thread(new Producer(repertory, 2), "producerThread" + i);
            producerThreads[i].start();
        }

        Thread[] consumerThreads = new Thread[20];
        for (int i = 0; i < consumerThreads.length; i++) {
            producerThreads[i] = new Thread(new Consumer(repertory, 2), "consumerThread" + i);
            producerThreads[i].start();
        }
    }
}
