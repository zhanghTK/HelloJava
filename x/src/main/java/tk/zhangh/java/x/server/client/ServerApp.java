package tk.zhangh.java.x.server.client;

import tk.zhangh.java.x.server.Server;

/**
 * Created by ZhangHao on 2017/3/13.
 */
public class ServerApp {
    public static void main(String[] args) {
        Server server = new Server();
        server.setPort(8090);
        server.start();
        server.init();
    }
}
