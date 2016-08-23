package tk.zhangh.java.net.rpc;

import tk.zhangh.java.net.rpc.service.SayHelloService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by ZhangHao on 2016/8/22.
 */
public class Consumer {
    public static void main(String[] args) {
        //接口名称
        String interfaceName = SayHelloService.class.getName();
        // todo 资源管理
        try {
            //需要执行的远程方法
            Method method = SayHelloService.class.getMethod("sayHello", java.lang.String.class);
            //需要传递到远程端的参数
            Object[] agrs = {"world"};
            Socket socket = new Socket("127.0.0.1", 10808);
            //将方法名称和参数传递到远端
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(interfaceName);//接口名称
            outputStream.writeUTF(method.getName());//方法名称
            outputStream.writeObject(method.getParameterTypes());
            outputStream.writeObject(agrs);
            //从远端读取方法的执行结果
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Object result = inputStream.readObject();
            System.out.println("Consumer result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //关闭资源...
        }

    }
}
