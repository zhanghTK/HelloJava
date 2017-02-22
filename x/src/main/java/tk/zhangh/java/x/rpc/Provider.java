package tk.zhangh.java.x.rpc;


import tk.zhangh.java.x.lifecycle.DefaultLifeCycle;
import tk.zhangh.java.x.lifecycle.LifecycleException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RPC服务提供者
 * Created by ZhangHao on 2016/8/22.
 */
public class Provider extends DefaultLifeCycle {

    private static final int port = 10808;
    private static Map<String, Object> beanContext = new ConcurrentHashMap<>();

    static {
        beanContext.put(SayHelloServiceImpl.class.getName(), new SayHelloServiceImpl());
    }

    private ServerSocket acceptor = null;

    @Override
    protected void init0() throws LifecycleException {
        try {
            acceptor = new ServerSocket(port);
        } catch (IOException e) {
            throw new LifecycleException("open ServerSocket with port:" + port, e);
        }
    }

    @Override
    protected void start0() throws LifecycleException {
        try (Socket socket = acceptor.accept()) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            Operation operation = new Operation(socket);
            outputStream.writeObject(operation.operator());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class Operation {
        private String beanName;
        private String methodName;
        private Class<?>[] parameterTypes;
        private Object[] parameters;

        public Operation(Socket socket) {
            try {
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
                beanName = inStream.readUTF();
                methodName = inStream.readUTF();
                parameterTypes = (Class<?>[]) inStream.readObject();
                parameters = (Object[]) inStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public Object operator() {
            Object bean = beanContext.get(beanName);
            try {
                Method method = bean.getClass().getMethod(methodName, parameterTypes);
                return method.invoke(bean, parameters);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
