package tk.zhangh.java.jvm.load.Initialization;

/**
 * Created by ZhangHao on 2016/8/10.
 */
public class MethodHandleClass {
    static {
        System.out.println("MethodHandleClass init");
    }

    public static void testMethodHandle(String str) {
        System.out.println(str);
    }
}
