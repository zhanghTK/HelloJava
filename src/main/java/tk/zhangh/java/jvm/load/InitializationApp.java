package tk.zhangh.java.jvm.load;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 主动引用
 * Created by ZhangHao on 2016/8/10.
 */
public class InitializationApp {

    public void initialization1() {
        System.out.println("遇到new、getstatic、putstatic和invokestaic这4条字节码指令,如果类没有进行过初始化，则需要先触发类的初始化");
        new SuperClass();
    }

    public void initialization2() {
        System.out.println("对类进行反射调用的时候，如果类没有进行过初始化，则需要先触发其初始化");
        try {
            Class.forName("tk.zhangh.java.jvm.load.SuperClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialization3() {
        System.out.println("当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要触发其父类的初始化");
        new SubClass();
    }

    public void initialization4() {
        System.out.println("" +
                "当使用JDK 1.7 的动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的结果是\n" +
                "REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄\n" +
                "这个方法句柄对应的类没有进行过初始化，则需要先触发其初始化\"");
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            MethodHandle testMethodHandle = lookup.findStatic(MethodHandleClass.class, "testMethodHandle", MethodType.methodType(void.class, String.class));
            try {
                testMethodHandle.invoke("");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
