package tk.zhangh.java.x.chat.server;

import lombok.Data;
import lombok.experimental.Accessors;
import tk.zhangh.toolkit.IoUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by ZhangHao on 2017/3/16.
 */
@Data
@Accessors(chain = true)
public class User implements Runnable {

    private ClientContent clientContent;

    private String name;

    private DataInputStream inStream;

    private DataOutputStream outStream;

    private boolean isRunning = true;

    public User(Socket socket, ClientContent clientContent) {
        try {
            this.clientContent = clientContent;
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            name = inStream.readUTF();
        } catch (IOException e) {
            doException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            sendMessage(clientContent.getUsers(this), receiveMessage());
        }
    }

    public void sendMessage(User user, String message) {
        String content = "【私信】" + this.name + ":" + message;
        user.rendMessage0(content);
    }

    public void sendMessage(List<User> users, String message) {
        String content = "【群发】" + this.name + ":" + message;
        users.forEach(user -> user.rendMessage0(content));
    }

    public void sendWelcome(List<User> users) {
        sendMessage(this, "欢迎" + this.getName());
        sendMessage(users, this.getName() + "已上线");
    }

    public String receiveMessage() {
        try {
            return inStream.readUTF();
        } catch (IOException e) {
            doException(e);
        }
        return "";
    }

    private void rendMessage0(String message) {
        try {
            getOutStream().writeUTF(message);
            getOutStream().flush();
        } catch (IOException e) {
            doException(e);
        }
    }

    private void doException(Exception e) {
        e.printStackTrace();
        IoUtils.close(inStream);
        IoUtils.close(outStream);
        isRunning = false;
    }
}
