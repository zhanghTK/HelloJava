package tk.zhangh.java.thread.concurrent.delay;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Cache使用实例
 * Created by ZhangHao on 16/9/11.
 */
public class CacheTest {

    /**
     * Cache使用示例
     */
    @Test
    public void testCache() throws Exception {
        Cache<String, String> cache = new Cache<>();
        cache.put("hello", "world", 3, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(2);
        System.out.println(cache.get("hello"));

        TimeUnit.SECONDS.sleep(2);
        System.out.println(cache.get("world"));
    }

    private Cache<String, String> cache;

    @Before
    public void init() {
        cache = new Cache<>();
    }

    @Test
    public void testPut() throws Exception {
        testGetAndPut();
    }

    @Test
    public void testGet() throws Exception {
        testGetAndPut();
    }

    private void testGetAndPut() throws Exception{
        cache.put("hello", "world", 1, TimeUnit.SECONDS);
        Assert.assertEquals(cache.get("hello"), "world");

        cache.put("hello", "java", 1, TimeUnit.SECONDS);
        Assert.assertEquals(cache.get("hello"), "java");

        TimeUnit.SECONDS.sleep(2);
        Assert.assertNull(cache.get("hello"));
    }
}