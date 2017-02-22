package tk.zhangh.java.x.lifecycle;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static tk.zhangh.java.x.lifecycle.LifecycleState.*;

/**
 * 生命周期抽象类
 * 处理声明周期状态、异常等问题，提供基本骨架
 * Created by ZhangHao on 2016/6/15.
 */
public abstract class AbstractLifeCycle implements Lifecycle {

    private List<LifecycleListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * state 代表当前生命周期状态
     */
    private LifecycleState state = NEW;

    @Override
    public final synchronized void init() throws LifecycleException {
        if (state != NEW) {
            return;
        }
        setStateAndFireEvent(INITIALIZING);
        try {
            init0();
        } catch (Throwable t) {
            doThrowable(t, "init");
        }
        setStateAndFireEvent(INITIALIZED);
    }

    protected abstract void init0() throws LifecycleException;

    @Override
    public final synchronized void start() throws LifecycleException {
        if (state == NEW) {
            init();
        }

        if (state != INITIALIZED) {
            return;
        }

        setStateAndFireEvent(STARTING);
        try {
            start0();
        } catch (Throwable t) {
            doThrowable(t, "start");
        }
        setStateAndFireEvent(STARTED);
    }

    protected abstract void start0() throws LifecycleException;

    @Override
    public final synchronized void suspend() throws LifecycleException {
        if (state == SUSPENDING || state == SUSPENDED) {
            return;
        }

        if (state != LifecycleState.STARTED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.SUSPENDING);
        try {
            suspend0();
        } catch (Throwable t) {
            doThrowable(t, "suspend");
        }
        setStateAndFireEvent(LifecycleState.SUSPENDED);
    }

    protected abstract void suspend0() throws LifecycleException;

    @Override
    public final synchronized void resume() throws LifecycleException {
        if (state != SUSPENDED) {
            return;
        }
        setStateAndFireEvent(RESUMING);
        try {
            resume0();
        } catch (Throwable t) {
            doThrowable(t, "resume");
        }
        setStateAndFireEvent(RESUMED);
    }

    protected abstract void resume0() throws LifecycleException;

    @Override
    public final synchronized void destroy() throws LifecycleException {
        if (state == DESTROYING || state == DESTROYED) {
            return;
        }
        setStateAndFireEvent(DESTROYING);
        try {
            destroy0();
        } catch (Throwable t) {
            doThrowable(t, "destroy");
        }
        setStateAndFireEvent(DESTROYED);
    }

    protected abstract void destroy0() throws LifecycleException, IOException;

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        listeners.remove(listener);
    }

    private void fireLifecycleEvent(LifecycleEvent event) {
        listeners.forEach(lifecycleListener -> lifecycleListener.lifecycleEvent(event));
    }

    protected synchronized LifecycleState getState() {
        return state;
    }

    private synchronized void setStateAndFireEvent(LifecycleState newState) throws LifecycleException {
        state = newState;
        fireLifecycleEvent(new LifecycleEvent(state));
    }

    private void doThrowable(Throwable throwable, String action) {
        setStateAndFireEvent(FAILED);
        if (throwable instanceof LifecycleException) {
            throw (LifecycleException) throwable;
        } else {
            throw new LifecycleException(
                    String.format("Failed to %s %s, Error Msg: %s", action, toString(), throwable.getMessage()), throwable);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
