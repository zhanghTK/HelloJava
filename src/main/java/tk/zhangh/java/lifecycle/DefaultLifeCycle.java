package tk.zhangh.java.lifecycle;

import tk.zhangh.java.exception.LifecycleException;

/**
 * 生命周期默认实现
 * 所有实现为空
 * Created by ZhangHao on 2016/6/15.
 */
public class DefaultLifeCycle extends AbstractLifeCycle{
    @Override
    protected void init0() throws LifecycleException {

    }

    @Override
    protected void start0() throws LifecycleException {

    }

    @Override
    protected void suspend0() throws LifecycleException {

    }

    @Override
    protected void resume0() throws LifecycleException {

    }

    @Override
    protected void destroy0() throws LifecycleException {

    }
}
