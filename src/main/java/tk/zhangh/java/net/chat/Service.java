package tk.zhangh.java.net.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器端主程序
 * Created by ZhangHao on 2016/6/6.
 */
public class Service {
    // 保存所有客户端连接
    private List<ChannelThread> allClient = new ArrayList<ChannelThread>();

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
        // 启动服务端socket
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            // 接收客户端socket
            Socket client = server.accept();
            // 客户端请求包装成管道线程
            ChannelThread channel = new ChannelThread(client, allClient);
            // 启动管道线程
            new Thread(channel).start();
        }
    }
}
