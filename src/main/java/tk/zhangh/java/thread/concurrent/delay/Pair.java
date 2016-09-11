package tk.zhangh.java.thread.concurrent.delay;

/**
 * 简单的Key,Value对象
 * Created by ZhangHao on 16/9/11.
 */
public class Pair<K,V> {
    protected final K key;
    protected final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
