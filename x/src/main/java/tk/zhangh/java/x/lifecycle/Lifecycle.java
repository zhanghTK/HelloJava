package tk.zhangh.java.x.lifecycle;

/**
 * 生命周期管理接口
 * Created by ZhangHao on 2016/6/15.
 */
interface Lifecycle {

    /**
     * 初始化
     */
    void init() throws LifecycleException;

    /**
     * 启动
     */
    void start() throws LifecycleException;

    /**
     * 暂停
     */
    void suspend() throws LifecycleException;

    /**
     * 恢复
     */
    void resume() throws LifecycleException;

    /**
     * 销毁
     */
    void destroy() throws LifecycleException;

    /**
     * 添加生命周期监听器
     */
    void addLifecycleListener(LifecycleListener listener);

    /**
     * 删除生命周期监听器
     */
    void removeLifecycleListener(LifecycleListener listener);
}
