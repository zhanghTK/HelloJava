package tk.zhangh.java.exception;

/**
 * IO异常包装类
 * Created by ZhangHao on 2016/7/27.
 */
public class IOExceptionWrapping extends RuntimeException {
    public IOExceptionWrapping(Throwable throwable, String message) {
        super(message, throwable);
    }
}
