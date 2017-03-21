package tk.zhangh.toolkit;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by ZhangHao on 2017/3/16.
 */
public class IoUtils {
    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
