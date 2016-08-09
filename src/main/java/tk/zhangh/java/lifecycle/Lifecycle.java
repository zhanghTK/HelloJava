package tk.zhangh.java.lifecycle;

import tk.zhangh.java.exception.LifecycleException;

/**
 * 生命周期管理接口
 * Created by ZhangHao on 2016/6/15.
 */
interface Lifecycle {
    /**
     * 初始化
     *
     * @throws LifecycleException
     */
    void init() throws LifecycleException;

    /**
     * 启动
     *
     * @throws LifecycleException
     */
    void start() throws LifecycleException;

    /**
     * 暂停
     *
     * @throws LifecycleException
     */
    void suspend() throws LifecycleException;

    /**
     * 恢复
     *
     * @throws LifecycleException
     */
    void resume() throws LifecycleException;

    /**
     * 销毁
     *
     * @throws LifecycleException
     */
    void destroy() throws LifecycleException;

    /**
     * 添加生命周期监听器
     *
     * @param listener
     */
    void addLifecycleListener(LifecycleListener listener);

    /**
     * 删除生命周期监听器
     *
     * @param listener
     */
    void removeLifecycleListener(LifecycleListener listener);
}
