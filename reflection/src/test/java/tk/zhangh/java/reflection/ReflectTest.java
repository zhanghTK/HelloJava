package tk.zhangh.java.reflection;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射帮助类的简单使用
 * Created by ZhangHao on 2017/4/21.
 */
public class ReflectTest {
    @Test
    public void newInstance() {
        String string = Reflect.on(String.class).create("Hello World").get();
        Assert.assertEquals("Hello World", string);
    }

    @Test
    public void getField() {
        char pathSeparatorChar = Reflect.on(File.class).create("/sdcard/droidyue.com").field("pathSeparatorChar").get();
        Assert.assertEquals(';', pathSeparatorChar);
    }

    @Test
    public void updateField() {
        String setValue = Reflect.on(File.class).create("/sdcard/drodiyue.com").set("path", "fakepath").get("path");
        Assert.assertEquals("fakepath", setValue);
    }

    @Test
    public void invokeMethod() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Hello");
        arrayList.add("World");
        int value = Reflect.on(arrayList).call("hugeCapacity", 12).get();
        Assert.assertEquals(Integer.MAX_VALUE - 8, value);
    }
}