package tk.zhangh.java.net.chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 客户端发送数据线程
 * Created by ZhangHao on 2016/6/6.
 */
public class SendThread implements Runnable{
    private String name;
    // 输入流，接收用户输入
    private BufferedReader console;
    // 输出流，向发武器发送
    private DataOutputStream dataOutputStream;
    // 线程是否继续
    private boolean isRunning = true;

    /**
     * 构造方法，初始化输入输出流
     * @param socket
     */
    public SendThread(Socket socket, String name){
        try {
            this.console = new BufferedReader(new InputStreamReader(System.in));
            this.name = name;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            sendMsg(this.name);
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(console, dataOutputStream);
            e.printStackTrace();
        }
    }

    /**
     * 启动线程
     */
    @Override
    public void run() {
        while (isRunning){
            sendMsg(getMsg());
        }
    }

    /**
     * 发送数据
     */
    public void sendMsg(String msg){
        try {
            if (msg != null && !msg.equals("")){
                dataOutputStream.writeUTF(msg);
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(console, dataOutputStream);
            e.printStackTrace();
        }
    }

    /**
     * 接收用户输入
     * @return
     */
    public String getMsg(){
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
