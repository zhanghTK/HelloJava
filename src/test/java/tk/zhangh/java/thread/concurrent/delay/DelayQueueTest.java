package tk.zhangh.java.thread.concurrent.delay;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue使用Demo
 * Created by ZhangHao on 16/9/12.
 */
public class DelayQueueTest {
    public static void main(String[] args) {
        DelayQueue<DelayedElement> delayQueue = new DelayQueue<>();

        // 打印延迟队列元素个数
        printQueueSize(delayQueue);

        //生产者
        producer(delayQueue);

        //消费者
        consumer(delayQueue);
    }

    /**
     * 每秒打印延迟队列中的对象个数
     */
    private static void printQueueSize(DelayQueue<DelayedElement> delayQueue) {
        new Thread() {
            @Override
            public void run() {
                while (true){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("delayQueue size:"+delayQueue.size());
                }
            }
        }.start();
    }

    /**
     * 每100毫秒创建一个对象，放入延迟队列，延迟时间1毫秒
     * @param delayQueue
     */
    private static void producer(DelayQueue<DelayedElement> delayQueue) {
        new Thread() {
            @Override
            public void run() {
                while (true){
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    DelayedElement element = new DelayedElement(1000, "test");
                    delayQueue.offer(element);
                }
            }
        }.start();

    }

    /**
     * 消费者，从延迟队列中获得数据,进行处理
     * @param delayQueue
     */
    private static void consumer(DelayQueue<DelayedElement> delayQueue) {
        new Thread(){
            @Override
            public void run() {
                while (true){
                    DelayedElement element = null;
                    try {
                        element =  delayQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(System.currentTimeMillis()+"---"+element);
                }
            }
        }.start();
    }

    static class DelayedElement implements Delayed {

        private final long delay;  // 延迟时间
        private final long expire;  // 到期时间
        private final String msg;   // 数据
        private final long create; // 创建时间

        public DelayedElement(long delay, String msg) {
            this.delay = delay;
            this.msg = msg;
            expire = System.currentTimeMillis() + delay;  // 到期时间 = 当前时间 + 延迟时间
            create = System.currentTimeMillis();
        }

        /**
         * 获得延迟时间
         * @param unit 时间单位
         * @return 延迟时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis() , TimeUnit.NANOSECONDS);
        }

        /**
         * 用于延迟队列内部比较排序   当前时间的延迟时间 - 比较对象的延迟时间
         * @param other 比较对象
         * @return 比较结果
         */
        @Override
        public int compareTo(Delayed other) {
            return (int) (this.getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
        }

        @Override
        public String toString() {
            final StringBuilder stringBuilder = new StringBuilder("DelayedElement{");
            stringBuilder.append("delay=").append(delay);
            stringBuilder.append(", expire=").append(expire);
            stringBuilder.append(", msg='").append(msg).append('\'');
            stringBuilder.append(", create=").append(create);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }
}
