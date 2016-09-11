package tk.zhangh.java.thread.concurrent.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 键值对象包装对象,队列元素
 * Created by ZhangHao on 16/9/11.
 */
public class DelayItem<T> implements Delayed {
    /**
     * 起始时间
     */
    private static final long NANO_ORIGIN = System.nanoTime();

    /**
     * 当前时间和起始时间的时间差
     * @return 当前时间和起始时间的时间差
     */
    static final long now() {
        return System.nanoTime() - NANO_ORIGIN;
    }

    /**
     * 递增序列
     */
    private static final AtomicLong sequencer = new AtomicLong(0);

    private final long sequenceNumber;

    /**
     * 失效时间(时刻)
     */
    private final long time;

    /**
     * 包装的键值对象
     */
    private final T item;

    public DelayItem(T submit, long timeout) {
        this.item = submit;
        this.time = now() + timeout;
        this.sequenceNumber = sequencer.getAndIncrement();
    }

    /**
     * 获取键值对象
     * @return 键值对象
     */
    public T getItem() {
        return this.item;
    }

    /**
     * 获取延迟时间
     * @param unit 时间单位
     * @return 延迟时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time - now(), TimeUnit.NANOSECONDS);
    }

    /**
     * 优先级比较
     * @param other 比较对象
     * @return 比较解决
     */
    @Override
    public int compareTo(Delayed other) {
        if (other == this) // compare zero ONLY if same object
            return 0;
        if (other instanceof DelayItem) {
            DelayItem x = (DelayItem) other;
            long diff = time - x.time;
            if (diff < 0)
                return -1;
            else if (diff > 0)
                return 1;
            else if (sequenceNumber < x.sequenceNumber)
                return -1;
            else
                return 1;
        }
        long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }
}
