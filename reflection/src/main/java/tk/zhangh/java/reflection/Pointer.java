package tk.zhangh.java.reflection;

import sun.misc.Unsafe;

/**
 * 使用反射获取Unsafe对象
 * Created by ZhangHao on 2017/4/21.
 */
public class Pointer {
    public static Unsafe getUnsafeInstance() {
        return Reflect.on(Unsafe.class).field("theUnsafe").get();
    }
}
