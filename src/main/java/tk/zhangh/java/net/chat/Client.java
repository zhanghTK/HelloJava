package tk.zhangh.java.net.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 客户端主程序
 * Created by ZhangHao on 2016/6/6.
 */
public class Client {
    public static void main(String[] args) throws Exception{
        try {
            new Client().stat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stat()throws Exception{
        String name = login();
        Socket client = new Socket("localhost", 8888);
        new Thread(new SendThread(client, name)).start();
        new Thread(new ReceiveThread(client)).start();
    }

    public String login(){
        System.out.println("请输入一个名称：");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String name = "";
        while (name == null || name.equals("")){
            try {
                name = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return name;
    }
}
