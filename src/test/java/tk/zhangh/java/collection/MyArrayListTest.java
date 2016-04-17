package tk.zhangh.java.collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试模拟ArrayList
 * Created by ZhangHao on 2016/4/17.
 */
public class MyArrayListTest {
    private MyArrayList list1 = new MyArrayList();
    private MyArrayList list2 = new MyArrayList(5);



    @Test
    public void testSize() throws Exception {
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        Assert.assertEquals(list1.size(), 5);
    }

    @Test
    public void testIsEmpty() throws Exception {
        for (int i = 0; i < 5; i++) {
            list2.add(i);
        }
        Assert.assertEquals(list1.isEmpty(), true);
        Assert.assertEquals(list2.isEmpty(),false);
    }

    @Test
    public void testAdd() throws Exception {
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(list1.get(i), i);
        }
    }

    @Test
    public void testGet() throws Exception {
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(list1.get(i), i);
        }
    }

    @Test
    public void testContains() throws Exception {
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        Assert.assertTrue(list1.contains(1));
    }

    @Test
    public void testIndexOf() throws Exception {
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        Assert.assertEquals(list1.indexOf(3), 3);
    }

    @Test
    public void testLastIndexOf() throws Exception {
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        list1.add(2);
        Assert.assertEquals(list1.lastIndexOf(2), 5);
    }

    @Test
    public void testSet() throws Exception {
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        list1.set(0, "5");
        Assert.assertEquals(list1.get(0), "5");
    }

    @Test
    public void testRemove() throws Exception {
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        list1.remove(0);
        Assert.assertEquals(list1.get(0), 1);
    }
}