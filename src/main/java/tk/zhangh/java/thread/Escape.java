package tk.zhangh.java.thread;

/**
 * 在构造方法内创建线程造成逸出例子
 * Created by ZhangHao on 2016/9/9.
 */
public class Escape {
    private String firstName = "firstName";
    private String lastName = null;

    public Escape() throws Exception{
        new Thread(new MyClass()).start();
        new Thread(new MyClass()).start();

        Thread.sleep(100L);

        lastName = "lastName";
    }

    private class MyClass implements Runnable {
        @Override
        public void run() {
            System.out.println(Escape.this.lastName);
        }
    }

    public static void main(String[] args) {
        try {
            new Escape();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
