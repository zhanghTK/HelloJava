package tk.zhangh.java.io;

/**
 * IO异常包装类
 * Created by ZhangHao on 2017/3/21.
 */
public class IOExceptionWrapping extends RuntimeException {
    public IOExceptionWrapping(String message, Throwable throwable) {
        super(message, throwable);
    }
}
