package tk.zhangh.java.net.chat;

import java.net.Socket;

/**
 * 客户端主程序
 * Created by ZhangHao on 2016/6/6.
 */
public class Client {
    public static void main(String[] args) throws Exception{
        Socket client = new Socket("localhost", 8888);
        new Thread(new SendThread(client)).start();
        new Thread(new ReceiveThread(client)).start();
    }
}
