package tk.zhangh.java.concurrent;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 错误使用可变对象线程不安全
 * Created by ZhangHao on 2017/4/19.
 */
public class DateFormatError {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            service.execute(() -> {
                String time = "2017-04-19 19:29:" + new Random().nextInt(100);
                try {
                    System.out.println(time + " pared:" + DATE_FORMAT.parse(time).getTime());
                } catch (Exception e) {
                    System.out.println(time + " pared error");
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
