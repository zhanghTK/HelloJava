package tk.zhangh.java.x.lifecycle;

/**
 * 生命周期监听器
 * Created by ZhangHao on 2016/6/15.
 */
public interface LifecycleListener {
    /**
     * 对生命周期事件进行处理
     *
     * @param event 生命周期事件
     */
    void lifecycleEvent(LifecycleEvent event);
}
