package tk.zhangh.java.concurrent.pattern.single;

/**
 * 饿汉单例
 * 缺点：在访问STATES字段时会创建对应实例
 * Created by ZhangHao on 2017/3/30.
 */
public class Singleton {
    private static final Singleton instance = new Singleton();
    private static int STATUS = 1;

    private Singleton() {
        System.out.println("create single");
    }

    public static Singleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(Singleton.STATUS);
    }
}
