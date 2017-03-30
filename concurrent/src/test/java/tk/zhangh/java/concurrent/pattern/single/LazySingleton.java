package tk.zhangh.java.concurrent.pattern.single;

/**
 * 懒汉单例
 * 每次获取单例都要获取锁
 * Created by ZhangHao on 2017/3/30.
 */
public class LazySingleton {
    private static int status = 1;
    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("LazySingleton is create");
    }

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(LazySingleton.status);
    }
}
