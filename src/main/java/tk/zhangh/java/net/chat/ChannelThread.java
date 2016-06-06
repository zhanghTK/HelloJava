package tk.zhangh.java.net.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * 连接客户端socket的管道
 * Created by ZhangHao on 2016/6/6.
 */
public class ChannelThread implements Runnable{
    // 所有客户端连接
    private List<ChannelThread> allClient;
    // 输入流，接收客户端输入
    private DataInputStream dataInputStream;
    // 输出流，向客户端输出
    private DataOutputStream dataOutputStream;
    // 当前线程是否继续
    private boolean isRunning = true;

    /**
     * 构造方法，初始化输入、输出流，所有客户端连接
     * @param client 当前客户端socket
     * @param allClient 所有客户端socket管道
     */
    public ChannelThread(Socket client, List<ChannelThread> allClient) {
        try {
            this.allClient = allClient;
            this.dataInputStream = new DataInputStream(client.getInputStream());
            this.dataOutputStream = new DataOutputStream(client.getOutputStream());
            allClient.add(this);
        } catch (IOException e) {
            CloseUtil.closeAll(dataInputStream, dataOutputStream);
            isRunning = false;
            allClient.remove(this);
            e.printStackTrace();
        }
    }

    /**
     * 接收客户端数据
     * @return
     */
    private String receiveMsg(){
        String msg = "";
        try {
            msg = dataInputStream.readUTF();
        } catch (IOException e) {
            CloseUtil.closeAll(dataInputStream);
            isRunning = false;
            allClient.remove(this);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 向客户端发送数据
     * @param msg
     */
    public void sendMsg(String msg){
        if (msg == null || msg.equals("")){
            return;
        }
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException e) {
            CloseUtil.closeAll(dataOutputStream);
            isRunning = false;
            allClient.remove(this);
            e.printStackTrace();
        }
    }

    /**
     * 向除本线程外所有客户端线程发送数据
     */
    public void sendAll(){
        String msg = receiveMsg();
        for(ChannelThread channel : allClient){
            if (channel == this){
                continue;
            }
            channel.sendMsg(msg);
        }
    }

    /**
     * 启动线程
     */
    @Override
    public void run() {
        while (isRunning){
            sendAll();
        }
    }
}
