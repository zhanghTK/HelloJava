package tk.zhangh.java.jvm.load.Initialization;

/**
 * Created by ZhangHao on 2016/8/10.
 */
public class SubClass extends SuperClass{
    static {
        System.out.println("SubClass init!");
    }
}
