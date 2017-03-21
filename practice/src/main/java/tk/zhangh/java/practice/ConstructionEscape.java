package tk.zhangh.java.practice;

import java.util.concurrent.TimeUnit;

/**
 * 在构造方法内创建线程造成 逸出
 * Created by ZhangHao on 2017/3/18.
 */
public class ConstructionEscape {
    private String firstName = "firstName";
    private String lastName = null;

    public ConstructionEscape() {
        Runnable runnable = () -> System.out.println(lastName);
        new Thread(runnable).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lastName = "lastName";
        new Thread(runnable).start();
    }

    public static void main(String[] args) {
        new ConstructionEscape();
    }
}
