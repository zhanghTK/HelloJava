package tk.zhangh.java.x.chat.client;

import tk.zhangh.toolkit.IoUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 客户端接收数据线程
 * Created by ZhangHao on 2016/6/6.
 */
public class ReceiveThread implements Runnable{
    // 输入流，接收服务器返回数据
    private DataInputStream dataInputStream;
    // 线程是否继续执行
    private boolean isRunning = true;

    // 构造方法，初始化输入流
    public ReceiveThread(Socket client) {
        try {
            this.dataInputStream = new DataInputStream(client.getInputStream());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * 启动线程
     */
    @Override
    public void run() {
        while (isRunning){
            System.out.println(receiveMsg());
        }
    }

    /**
     * 接收服务器返回数据
     * @return
     */
    public String receiveMsg(){
        String msg = "";
        try {
            msg = dataInputStream.readUTF();
        } catch (IOException e) {
            isRunning = false;
            IoUtils.close(dataInputStream);
            e.printStackTrace();
        }
        return msg;
    }
}
