package tk.zhangh.java.net.chat;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * Created by ZhangHao on 2016/6/6.
 */
public class CloseUtil {
    public static void closeAll(Closeable ...io){
        for (Closeable tmp : io){
            if (tmp != null){
                try {
                    tmp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
