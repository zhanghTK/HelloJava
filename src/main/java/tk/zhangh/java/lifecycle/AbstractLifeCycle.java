package tk.zhangh.java.lifecycle;

import tk.zhangh.java.exception.LifecycleException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private LifecycleState state = LifecycleState.NEW;


    @Override
    public final synchronized void init() throws LifecycleException {
        if (state != LifecycleState.NEW) {
            return;
        }
        setStateAndFireEvent(LifecycleState.INITIALIZING);
        try {
            init0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(
                        String.format("Failed to initialize %s, Error Msg: %s", toString(), t.getMessage()),
                        t);
            }
        }
        setStateAndFireEvent(LifecycleState.INITIALIZED);
    }

    protected abstract void init0() throws LifecycleException;

    @Override
    public final synchronized void start() throws LifecycleException {
        if (state == LifecycleState.NEW) {
            init();
        }

        if (state != LifecycleState.INITIALIZED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.STARTING);
        try {
            start0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(
                        String.format("Failed to start %s, Error Msg: %s", toString(), t.getMessage()),
                        t);
            }
        }
        setStateAndFireEvent(LifecycleState.STARTED);
    }

    protected abstract void start0() throws LifecycleException;

    @Override
    public final synchronized void suspend() throws LifecycleException {
        if (state == LifecycleState.SUSPENDING || state == LifecycleState.SUSPENDED) {
            return;
        }

        if (state != LifecycleState.STARTED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.SUSPENDING);
        try {
            suspend0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(
                        String.format("Failed to suspend %s, Error Msg: %s", toString(), t.getMessage()),
                        t);
            }
        }
        setStateAndFireEvent(LifecycleState.SUSPENDED);
    }

    protected abstract void suspend0() throws LifecycleException;

    @Override
    public final synchronized void resume() throws LifecycleException {
        if (state != LifecycleState.SUSPENDED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.RESUMING);
        try {
            resume0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(
                        String.format("Failed to resume %s, Error Msg: %s", toString(), t.getMessage()),
                        t);
            }
        }
        setStateAndFireEvent(LifecycleState.RESUMED);
    }

    protected abstract void resume0() throws LifecycleException;

    @Override
    public final synchronized void destroy() throws LifecycleException {
        if (state == LifecycleState.DESTROYING || state == LifecycleState.DESTROYED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.DESTROYING);
        try {
            destroy0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(
                        String.format("Failed to destroy %s, Error Msg: %s", toString(), t.getMessage()),
                        t);
            }
        }
        setStateAndFireEvent(LifecycleState.DESTROYED);
    }

    protected abstract void destroy0() throws LifecycleException;

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        listeners.remove(listener);
    }

    private void fireLifecycleEvent(LifecycleEvent event) {
        for (LifecycleListener listener : listeners) {
            listener.lifecycleEvent(event);
        }
    }

    protected synchronized LifecycleState getState() {
        return state;
    }

    private synchronized void setStateAndFireEvent(LifecycleState newState) throws LifecycleException {
        state = newState;
        fireLifecycleEvent(new LifecycleEvent(state));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
