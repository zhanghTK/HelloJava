package tk.zhangh.java.x.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Server帮助类
 * Created by ZhangHao on 2017/3/13.
 */
public class ServerUtils {
    public static final String CRLF = "\r\n";

    public static final String BLANK = " ";

    public static String decode(String value) {
        try {
            return URLDecoder.decode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
