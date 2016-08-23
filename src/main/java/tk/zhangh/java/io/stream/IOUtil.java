package tk.zhangh.java.io.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by ZhangHao on 2016/8/16.
 */
public class IOUtil {
    private static Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static <T extends Closeable> void close(T ... objs) {
        for (T obj : objs) {
            try {
                if (obj != null) {
                    obj.close();
                }
            } catch (IOException e) {
                logger.error("close stream error");
                e.printStackTrace();
            }
        }
    }
}
