package tk.zhangh.java.practice;

import org.junit.Test;

import java.lang.ref.*;
import java.util.*;

/**
 * 各种引用类型对比
 * Created by ZhangHao on 2017/3/20.
 */
public class DiffReference {

    private void runFinalization() {
        System.gc();
        System.runFinalization();
    }

    @Test
    public void softReference() {
        ReferenceQueue<Map<String, Object>> queue = new ReferenceQueue<>();
        Map<String, Object> map = new HashMap<>();
        SoftReference<Map<String, Object>> softReference = new SoftReference<>(map, queue);
        Map cache = softReference.get();
        // 使用cache
        if (cache == null) {
            cache = new HashMap();
        }
        Reference reference;
        while ((reference = queue.poll()) != null) {
            // 清理cache
        }
    }

    @Test
    public void weakReference() {
        String string = new String("zhangh.tk");
        WeakReference<String> reference = new WeakReference<>(string);
        assert reference.get() != null;
        string = null;
        runFinalization();
        assert reference.get() == null;
    }

    @Test
    public void weakReferenceWithFinal() {
        String finalStr = "zhangh.tk";
        WeakReference<String> reference = new WeakReference<>(finalStr);
        assert reference.get() != null;
        finalStr = null;
        runFinalization();
        assert reference.get() != null;
    }

    @Test
    public void weakMap() {
        WeakHashMap<String, String> weakHashMap = new WeakHashMap<>();
        weakHashMap.put("key1", "value1");
        weakHashMap.put(new String("key2"), "value2");
        assert weakHashMap.size() == 2;
        runFinalization();
        assert weakHashMap.size() == 1;
    }

    @Test
    public void weakHashMapOOM() {
        List<WeakHashMap<byte[][], byte[][]>> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            WeakHashMap<byte[][], byte[][]> map = new WeakHashMap<>();
            map.put(new byte[10000][1000], new byte[10000][1000]);
            list.add(map);
            runFinalization();
        }
    }

    @Test
    public void weakHashMapNotOOM() {
        List<WeakHashMap<byte[][], byte[][]>> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            WeakHashMap<byte[][], byte[][]> map = new WeakHashMap<>();
            map.put(new byte[10000][1000], new byte[10000][1000]);
            list.add(map);
            runFinalization();
            map.size();
        }
    }

    @Test
    public void reclaimed() {
        Reclaimable reclaimable = new Reclaimable();
        reclaimable = null;
        runFinalization();
        assert Reclaimable.staticVar != null;
        Reclaimable.staticVar = null;
        runFinalization();
        assert Reclaimable.staticVar == null;
    }

    @Test
    public void weakQueueWithUnReclaimed() {
        class A {
        }
        ReferenceQueue<A> queue = new ReferenceQueue<>();
        WeakReference<A> reference = new WeakReference<>(new A(), queue);
        assert reference.get() != null;
        assert queue.poll() == null;
        runFinalization();
        assert reference.get() == null;
        assert queue.poll() != null;
    }

    @Test
    public void weakQueueWithReclaimed() {
        ReferenceQueue<Reclaimable> queue = new ReferenceQueue<>();
        WeakReference<Reclaimable> reference = new WeakReference<>(new Reclaimable(), queue);
        assert reference.get() != null;
        assert queue.poll() == null;
        runFinalization();
        assert reference.get() == null;
        assert queue.poll() != null;
    }

    @Test
    public void phantomQueueWithUnReclaimed() {
        class A {
        }
        ReferenceQueue<A> queue = new ReferenceQueue<>();
        PhantomReference<A> reference = new PhantomReference<>(new A(), queue);
        assert reference.get() == null;
        assert queue.poll() == null;
        runFinalization();
        assert reference.get() == null;
        assert queue.poll() != null;
    }

    @Test
    public void phantomQueueWithReclaimed() {
        ReferenceQueue<Reclaimable> queue = new ReferenceQueue<>();
        PhantomReference<Reclaimable> reference = new PhantomReference<>(new Reclaimable(), queue);
        assert reference.get() == null;
        assert queue.poll() == null;
        runFinalization();
        assert reference.get() == null;
        assert queue.poll() == null;
        Reclaimable.staticVar = null;
        runFinalization();
        assert queue.poll() != null;
    }
}

class Reclaimable {
    static Reclaimable staticVar;

    @Override
    protected void finalize() throws Throwable {
        staticVar = this;
    }
}
