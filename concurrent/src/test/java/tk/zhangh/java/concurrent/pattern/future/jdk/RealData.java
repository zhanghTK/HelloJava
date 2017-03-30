package tk.zhangh.java.concurrent.pattern.future.jdk;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 业务类
 * Created by ZhangHao on 2017/3/30.
 */
public class RealData implements Callable<String> {
    private String para;

    public RealData(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(para);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
