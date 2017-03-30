package tk.zhangh.java.concurrent.pattern.single;

/**
 * 内部类实现单例
 * Created by ZhangHao on 2017/3/30.
 */
public class StaticSingleton {
    private static int status = 1;

    private StaticSingleton() {
        System.out.println("StaticSingleton is create");
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }

    public static void main(String[] args) {
        System.out.println(StaticSingleton.status);
    }

    private static class SingletonHolder {
        private static final StaticSingleton instance = new StaticSingleton();
    }
}
