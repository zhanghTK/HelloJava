package tk.zhangh.java.x.chat.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHao on 2017/3/16.
 */
public class ClientContent {
    private List<ClientChannel> clients;
    private List<User> users = new ArrayList<>();

    public void register(Socket client) {
        User user = new User(client, this);
        new Thread(user).start();
        users.add(user);
    }

    public void register(ClientChannel clientChannel) {
        clients.add(clientChannel);
        new Thread(clientChannel).start();
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<User> getUsers(User user) {
        ArrayList<User> result = new ArrayList<>(this.users);
        result.remove(user);
        return result;
    }
}
