package tk.zhangh.java.lifecycle;

/**
 * 生命周期事件
 * Created by ZhangHao on 2016/6/15.
 */
public class LifecycleEvent {
    private LifecycleState state;

    public LifecycleEvent(LifecycleState state) {
        this.state = state;
    }

    /**
     * @return the state
     */
    public LifecycleState getState() {
        return state;
    }
}
