package tk.zhangh.java.x.chat.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端主程序
 * Created by ZhangHao on 2016/6/6.
 */
public class Service {

    Dispatcher dispatcher = new Dispatcher();

    public static void main(String[] args) {
        try {
            new Service().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端启动入口
     * @throws Exception
     */
    public void start()throws Exception{
        ServerSocket server = new ServerSocket(19999);
        while (true) {
            Socket client = server.accept();
            dispatcher.register(client);
        }
    }
}
