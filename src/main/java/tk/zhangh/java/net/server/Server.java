package tk.zhangh.java.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ZhangHao on 2016/6/6.
 */
public class Server {
    private ServerSocket server;
    private boolean isShutDown = false;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.receive();
        server.stop();
    }

    /**
     * 以默认8090端口启动服务器
     */
    public void start(){
        start(8090);
    }

    /**
     * 指定端口启动服务器
     * @param port 端口号
     */
    public void start(int port){
        try {
            server = new ServerSocket(port);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            stop();

        }
    }

    /**
     * 处理服务请求
     */
    public void receive(){
        try {
            while (!isShutDown) {
                Socket client = server.accept();
                new Thread(new Dispatcher(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        isShutDown = true;
        CloseUtil.closeServerSocket(server);
    }
}
