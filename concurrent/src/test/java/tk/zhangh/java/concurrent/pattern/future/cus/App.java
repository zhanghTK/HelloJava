package tk.zhangh.java.concurrent.pattern.future.cus;

/**
 * 客户端调用
 * Created by ZhangHao on 2017/3/30.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        Task task = new ClientThread().request("query");
        System.out.println(task.getResult());
    }
}
