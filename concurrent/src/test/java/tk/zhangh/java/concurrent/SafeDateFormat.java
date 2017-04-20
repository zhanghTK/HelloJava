package tk.zhangh.java.concurrent;

import lombok.AllArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用ThreadLocal确保线程安全
 * Created by ZhangHao on 2017/4/19.
 */
public class SafeDateFormat {
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            service.execute(new SafeDateFormat.ParseDate(i));
        }
    }

    @AllArgsConstructor
    public static class ParseDate implements Runnable {
        int i = 0;

        @Override
        public void run() {
            try {
                Date date = DATE_FORMAT.get().parse("2017-04-19 19:29:" + i % 60);
                System.out.println(i + ":" + date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
