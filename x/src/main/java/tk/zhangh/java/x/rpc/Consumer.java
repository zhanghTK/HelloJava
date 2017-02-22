package tk.zhangh.java.x.rpc;


import tk.zhangh.java.x.lifecycle.DefaultLifeCycle;
import tk.zhangh.java.x.lifecycle.LifecycleException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * RPC服务消费者
 * Created by ZhangHao on 2016/8/22.
 */
public class Consumer extends DefaultLifeCycle {

    private Socket socket;

    @Override
    protected void init0() throws LifecycleException {
        try {
            socket = new Socket("127.0.0.1", 10808);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Object call(String className, Method method, Object... parameters) {
        return call(className, method.getName(), method.getParameterTypes(), parameters);
    }

    public Object call(String className, String methodName, Class<?>[] parameterTypes, Object... parameters) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(className);
            outputStream.writeUTF(methodName);
            outputStream.writeObject(parameterTypes);
            outputStream.writeObject(parameters);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
