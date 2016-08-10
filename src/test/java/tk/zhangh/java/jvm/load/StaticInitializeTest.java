package tk.zhangh.java.jvm.load;

/**
 * 静态初始化实例
 * Created by ZhangHao on 2016/8/10.
 * 说明：
 * 首先加载main方法所在类：StaticInitializeTest。
 * 执行静态初始化
 *      创建StaticLoadTest实例
 *          执行构造代码块
 *          执行构造方法
 *              实例变量已被初始化
 *              静态变量未初始化
 *      执行静态代码块
 * 调用staticFunction静态方法，输出
 */
public class StaticInitializeTest {
    public static void main(String[] args) {
        staticFunction();
    }

    static StaticInitializeTest staticInitializeTest = new StaticInitializeTest();
    static {
        System.out.println("1");
    }
    // 构造代码块，在创建对象时被调用，每次创建对象都会被调用，优于构造方法调用
    {
        System.out.println("2");
    }
    StaticInitializeTest(){
        System.out.println("3");
        System.out.println("a=" + a + " b=" + b);
    }

    public static void staticFunction(){
        System.out.println("4");
    }
    int a = 110;
    static int b =112;
}
