package tk.zhangh.java.net.connection.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 1. 创建客户端，指定服务器ip，端口
 * 2. 接收数据，发送时数据
 * Created by ZhangHao on 2016/6/6.
 */
public class ClientDemo {
    public static void main(String[] args) throws Exception{
        Socket client = new Socket("localhost", 8888);
        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
        String msg = input();
        dataOutputStream.writeUTF(msg);
        dataOutputStream.flush();
        DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
        msg = dataInputStream.readUTF();
        System.out.println(msg);
    }

    public static String input(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
