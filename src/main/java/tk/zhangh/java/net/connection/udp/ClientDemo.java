package tk.zhangh.java.net.connection.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 1. 创建客户端，DatagramSocket类+指定端口
 * 2. 准备数据，字节数组
 * 3. 打包 DatagramPacket+服务器地址+端口
 * 4. 发送
 * 5. 释放资源
 * Created by ZhangHao on 2016/6/4.
 */
public class ClientDemo {
    public static void main(String[] args) throws Exception{
        DatagramSocket client = new DatagramSocket(6666);
        String msg = "Hello UDP";
        byte[] data = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, new InetSocketAddress("localhost", 8888));
        client.send(packet);
        client.close();
    }
}
