package tk.zhangh.java.jvm.load.Initialization;

/**
 * Created by ZhangHao on 2016/8/10.
 */
public class SuperClass {
    static {
        System.out.println("SuperClass init!");
    }

    /**
     * 类静态字段
     */
    public static int value = 123;
}
