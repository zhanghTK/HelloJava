package tk.zhangh.java.thread.concurrent.delay;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 使用DelayQueue创建Cache
 * Created by ZhangHao on 16/9/11.
 */
public class Cache<K,V> {

    private static final Logger LOG = Logger.getLogger(Cache.class.getName());

    private ConcurrentMap<K, V> cacheObjMap = new ConcurrentHashMap<>();

    private DelayQueue<DelayItem<Pair<K, V>>> queue = new DelayQueue<>();

    private Thread daemonThread;

    public Cache() {
        daemonThread = new Thread() {
            @Override
            public void run() {
                daemonCheck();
            }
        };
        daemonThread.setDaemon(true);
        daemonThread.setName("Cache Daemon");
        daemonThread.start();
    }

    private void daemonCheck() {
        if (LOG.isLoggable(Level.INFO))
            LOG.info("cache service started.");

        while (true) {
            try {
                // 阻塞,一直等待到期元素
                DelayItem<Pair<K, V>> delayItem = queue.take();
                if (delayItem != null) {
                    // 超时对象处理
                    Pair<K, V> pair = delayItem.getItem();
                    // 移除
                    cacheObjMap.remove(pair.key, pair.value);
                }
            } catch (InterruptedException e) {
                if (LOG.isLoggable(Level.SEVERE))
                    LOG.log(Level.SEVERE, e.getMessage(), e);
                break;
            }
        }

        if (LOG.isLoggable(Level.INFO))
            LOG.info("cache service stopped.");
    }

    /**
     * 添加缓存对象
     * @param key key
     * @param value value
     * @param time 缓存时间
     * @param unit 单位
     */
    public void put(K key, V value, long time, TimeUnit unit) {
        V oldValue = cacheObjMap.put(key, value);
        if (oldValue != null) {
            queue.remove(key);
        }
        long nanoTime = TimeUnit.NANOSECONDS.convert(time, unit);
        queue.put(new DelayItem<>(new Pair<>(key, value), nanoTime));
    }

    /**
     * 添加缓存对象
     * @param key key
     * @param value value
     * @param time 缓存时间,单位秒
     */
    public void put(K key, V value, long time) {
        put(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 获得缓存对象
     * @param key key
     * @return value
     */
    public V get(K key) {
        return cacheObjMap.get(key);
    }
}
