package tk.zhangh.java.jvm.reference;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.*;
import java.util.*;

/**
 * Created by ZhangHao on 2016/8/23.
 */
public class ReferenceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 手动调用垃圾回收（无法确保立即回收）
     */
    private void runFinalization() {
        System.gc();
        System.runFinalization();
        logger.info("运行GC(无法确保调用)");
    }

    /**
     * 测试弱引用
     */
    @Test
    public void testWeak() {
        String string = new String("Hello");
        WeakReference<String> reference = new WeakReference<>(string);
        Assert.assertEquals("Hello", reference.get());
        logger.info("通过弱引用获取对象：{}", reference.get());
        string = null;  // 手动置空，方便回收
        runFinalization();
        Assert.assertNull(reference.get());
        logger.info("通过弱引用获取对象：{}", reference.get());
    }

    /**
     * 测试弱引用引用常量
     */
    @Test
    public void testWeakFinal() {
        String finalStr = "Hello";

        WeakReference<String> reference = new WeakReference<>(finalStr);
        Assert.assertEquals("Hello", reference.get());
        logger.info("通过弱引用获取常量对象：{}", reference.get());
        finalStr = null;
        runFinalization();
        Assert.assertNotNull(reference.get());
        logger.info("通过弱引用获取常量对象：{}", reference.get());
    }

    /**
     * 测试WeakMap
     */
    @Test
    public void testWeakMap() {
        WeakHashMap<String, String> weakHashMap = new WeakHashMap<>();
        String string = "test";
        logger.info("向WeakHashMap分别添加一个普通对象、一个常量对象的key");
        weakHashMap.put("test1", string);
        weakHashMap.put(new String("test2"), string);
        Assert.assertEquals(2, weakHashMap.size());
        logger.info("WeakHashMap中键值对个数：{}", weakHashMap.size());
        runFinalization();
        Assert.assertEquals(1, weakHashMap.size());
        logger.info("WeakHashMap中键值对个数：{}", weakHashMap.size());
    }

    /**
     * 软引用基本访问使用模板
     */
    @Test
    public void testSoft() {
        ReferenceQueue  queue = new ReferenceQueue();
        Map object = new HashMap();  // 初始化cache
        SoftReference<Map> softReference = new SoftReference(object, queue);
        Map cache = softReference.get();

        // 使用cache

        // cache被清空清空
        if (cache == null) {
            cache = new HashMap();  // 重新初始化cache
        }


        Reference ref = null;
        while ((ref = queue.poll()) != null) {
            // 清理cache
        }
    }

    /**
     * WeakHashMap并不是你啥也干他就能自动释放内部不用的对象的
     * 需要将JVM内存指定较小，例如：-Xmx64m
     */
    @Test
    public void testWeakHashMapOOM() throws Exception{
        List<WeakHashMap<byte[][], byte[][]>> list = new ArrayList<>();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            WeakHashMap<byte[][], byte[][]> map = new WeakHashMap<>();
            map.put(new byte[1000][1000], new byte[1000][1000]);
            list.add(map);
            System.gc();
            System.runFinalization();
        }
    }

    /**
     * 在访问WeakHashMap内容时，才会触发释放虚引用可达的对象
     * 与上一个单元测试对比，将JVM内存指定较小，例如：-Xmx64m
     */
    @Test
    public void testWeakHashMapNotOOM() {
        List<WeakHashMap<byte[][], byte[][]>> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            WeakHashMap<byte[][], byte[][]> map = new WeakHashMap<>();
            map.put(new byte[1000][1000], new byte[1000][1000]);
            list.add(map);
            System.gc();
            System.runFinalization();
            map.size();
        }
    }

    /**
     * 一个简单的对象生命周期为：Unfinalized—>Finalizable->Finalized->Reclaimed
     * 当新建一个对象时，会置位该对象的一个内部标识finalizable。
     * 当某一点GC检查到该对象不可达时，就把该对象放入finalize queue(F queue)
     * 对象复活
     */
    @Test
    public void testReclaimed() {
        Reclaimable reclaimable = new Reclaimable();
        reclaimable = null;  // 将对象置为null，可被回收
        logger.info("Reclaimable.staticVar:{}", Reclaimable.staticVar);
        runFinalization();
        logger.info("Reclaimable.staticVar:{}", Reclaimable.staticVar);
        // 再次执行GC
        Reclaimable.staticVar = null;
        logger.info("手动将Reclaimable.staticVar置空");
        runFinalization();
        logger.info("Reclaimable.staticVar:{}", Reclaimable.staticVar);

    }

    /**
     * 使用弱引用队列管理不再生对象
     */
    @Test
    public void testWeakQueueUnReclaimed() {
        class A{}
        ReferenceQueue queue = new ReferenceQueue();
        WeakReference ref = new WeakReference(new A(), queue);
        Assert.assertNotNull(ref.get());
        logger.info("通过弱引用获取对象：{}", ref.get());
        Object obj = queue.poll();
        Assert.assertNull(obj);
        logger.info("弱引用队列内的元素：{}", obj);
        runFinalization();
        Assert.assertNull(ref.get());
        logger.info("通过弱引用获取对象：{}", ref.get());
        obj = queue.poll();
        Assert.assertNotNull(obj);
        logger.info("弱引用队列内的元素：{}", obj);
    }

    /**
     * 使用虚引用队列管理不再生对象
     */
    @Test
    public void testPhantomQueueUnReclaimed() {
        class A{}
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference ref = new PhantomReference(new A(), queue);
        Assert.assertNull(ref.get());
        logger.info("通过虚引用获取对象：{}", ref.get());
        Object obj = null;
        obj = queue.poll();
        Assert.assertNull(obj);
        logger.info("虚引用队列内的元素：{}", obj);
        runFinalization();
        Assert.assertNull(ref.get());
        logger.info("通过虚引用获取对象：{}", ref.get());
        obj = queue.poll();
        Assert.assertNotNull(obj);
        logger.info("弱引用队列内的元素：{}", obj);
    }

    /**
     * 使用弱引用队列管理再生对象
     */
    @Test
    public void testWeakQueueReclaimed() {
        ReferenceQueue queue = new ReferenceQueue();
        WeakReference ref = new WeakReference(new Reclaimable(), queue);
        Assert.assertNotNull(ref.get());
        logger.info("通过弱引用获取对象：{}", ref.get());
        Object obj = queue.poll();
        Assert.assertNull(obj);
        logger.info("弱引用队列内的元素：{}", obj);
        runFinalization();
        Assert.assertNull(ref.get());
        logger.info("通过弱引用获取对象：{}", ref.get());
        obj = queue.poll();
        logger.info("Reclaimable.staticVar:{}", Reclaimable.staticVar);
        Assert.assertNotNull(obj);
        logger.info("弱引用队列内的元素：{}", obj);
    }

    /**
     * 使用虚引用队列管理再生对象
     */
    @Test
    public void testPhantomQueueReclaimed() {
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference ref = new PhantomReference(new Reclaimable(), queue);
        Assert.assertNull(ref.get());  // 无法通过虚引用获取对象
        logger.info("通过虚引用获取对象：{}", ref.get());
        Object obj = queue.poll();
        Assert.assertNull(obj);  // GC运行前，虚引用队列为空
        logger.info("虚引用队列内的元素：{}", obj);
        // 第一次运行GC,finalize中对象重生
        runFinalization();
//        logger.info("finalize中对象重生");
        Assert.assertNull(ref.get());  // 无法通过虚引用获取对象
        logger.info("通过虚引用获取对象：{}", ref.get());
        obj = queue.poll();
        Assert.assertNull(obj);  // GC运行后，虚引用队列为仍然空
        logger.info("虚引用队列内的元素：{}", obj);
        Reclaimable.staticVar = null;  // 置空重生对象
        logger.info("置空重生对象");
        // 再次运行GC
        runFinalization();
        obj = queue.poll();
        Assert.assertNotNull(obj);  // GC运行后，虚引用队列不为空
        logger.info("虚引用队列内的元素：{}", obj);
    }
}

/**
 * 可再生对象
 * finalize方法把将要垃圾回收对象引用重新赋值给其他引用
 */
class Reclaimable {
    static Reclaimable staticVar;

    @Override
    protected void finalize() throws Throwable {
        // 对象赋值给一个static变量，该对象又可达了，此之谓对象再生
        staticVar = this;
    }
}