package tk.zhangh.java.x.chat.server;

import java.net.Socket;

/**
 * Created by ZhangHao on 2017/3/16.
 */
public class Dispatcher {

    private ClientContent clientContent = new ClientContent();

    public void register(Socket client) {
        clientContent.register(client);
    }
}
