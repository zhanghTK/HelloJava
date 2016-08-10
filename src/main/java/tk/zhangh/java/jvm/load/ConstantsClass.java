package tk.zhangh.java.jvm.load;

/**
 * Created by ZhangHao on 2016/8/10.
 */
public class ConstantsClass {
    static {
        System.out.println("ConstantsClass init!");
    }

    /**
     * 类常量
     */
    public static final int VALUE = 123;
}
