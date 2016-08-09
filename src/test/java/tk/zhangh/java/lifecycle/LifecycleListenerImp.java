package tk.zhangh.java.lifecycle;

/**
 * 测试生命周期监听器
 * Created by ZhangHao on 2016/6/15.
 */
public class LifecycleListenerImp implements LifecycleListener{
    /**
     * 对生命周期事件进行处理
     *
     * @param event 生命周期事件
     */
    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        LifecycleState state = event.getState();
        String log;
        log = state.toString();
        System.out.println(log + "......");
    }
}
