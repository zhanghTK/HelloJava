package tk.zhangh.java.x.lifecycle;

/**
 * 生命周期相关状态
 * Created by ZhangHao on 2016/6/15.
 */
public enum LifecycleState {
    INITIALIZING,  // 初始化中
    INITIALIZED,  // 初始化完成
    NEW,  // 新生
    STARTING, // 启动中
    STARTED,  // 启动完成
    SUSPENDING, // 正在暂停
    SUSPENDED,  // 正在恢复
    RESUMING, // 正在恢复
    RESUMED,  // 已经恢复
    DESTROYING, // 正在销毁
    DESTROYED,  // 已经销毁
    FAILED  // 失败
}
