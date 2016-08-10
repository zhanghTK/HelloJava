package tk.zhangh.java.jvm.load;

/**
 * Created by ZhangHao on 2016/8/10.
 */
public class NotInitializationApp {
    public void notInitialization1() {
        System.out.println("子类引用父类的静态字段，不会导致子类初始化");
        System.out.println(SubClass.value);
    }

    public void notInitialization2() {
        System.out.println("通过数组定义来引用类，不会触发此类的初始化");
        // 注意，这里的字节码指令是newarray而非new，那么这段代码的初始化就是类[SuperClass的初始化
        SuperClass[] sca = new SuperClass[10];
    }

    public void notInitialization3() {
        // 常量在编译阶段会存入调用类的常量池中，本质上没有直接引用到定义常量的类，
        // 因此不会触发定义常量的类的初始化
        System.out.println("通过数组定义来引用类，不会触发此类的初始化");
        System.out.println(ConstantsClass.VALUE);
    }
}
