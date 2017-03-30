package tk.zhangh.java.concurrent.pattern.future.cus;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 实际数据获取
 * Created by ZhangHao on 2017/3/30.
 */
public class RealTask implements Task {
    private String result;

    public void process(String query) {
        int length = query.length();
        try {
            TimeUnit.SECONDS.sleep(length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = new Date().toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
