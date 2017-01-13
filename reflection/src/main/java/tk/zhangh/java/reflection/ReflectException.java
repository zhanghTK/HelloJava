package tk.zhangh.java.reflection;

/**
 * 反射错误
 * Created by ZhangHao on 2017/1/10.
 */
public class ReflectException extends RuntimeException {
    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException() {
        super();
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }
}
