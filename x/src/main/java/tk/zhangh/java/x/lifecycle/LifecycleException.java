package tk.zhangh.java.x.lifecycle;

/**
 * 生命周期异常
 * Created by ZhangHao on 2016/6/15.
 */
public class LifecycleException extends RuntimeException {
    public LifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifecycleException(Throwable cause) {
        super(cause);
    }
}
