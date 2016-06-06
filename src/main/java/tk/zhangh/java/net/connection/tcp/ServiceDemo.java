package tk.zhangh.java.net.connection.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 1. 创建服务端，指定端口(ServerSocket)
 * 2. 接收客户端连接
 * 3. 发送数据，接收数据
 * Created by ZhangHao on 2016/6/6.
 */
public class ServiceDemo {
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(8888);
        Socket client = server.accept();
        DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());

        while (true) {
            String msg = dataInputStream.readUTF();
            System.out.println(msg);
            dataOutputStream.writeUTF("欢迎");
            dataOutputStream.flush();
        }
    }
}
