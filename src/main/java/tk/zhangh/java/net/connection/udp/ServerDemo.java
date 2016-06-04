package tk.zhangh.java.net.connection.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 1. 创建服务端DatagramSocket类+指定端口
 * 2. 准备接收容器：将字节数组封装成DatagramPacket
 * 3. 使用包接收数据
 * 4. 处理数据
 * 5. 释放资源
 * Created by ZhangHao on 2016/6/4.
 */
public class ServerDemo {
    public static void main(String[] args) throws Exception{
        DatagramSocket server = new DatagramSocket(8888);
        byte[] container = new byte[1024];
        DatagramPacket packet = new DatagramPacket(container, container.length);
        // 阻塞接收
        server.receive(packet);
        byte[] data = packet.getData();
        int len = packet.getLength();
        System.out.println(new String(data, 0, len));
        server.close();
    }
}
