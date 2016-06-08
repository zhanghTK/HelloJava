package tk.zhangh.java.net.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Created by ZhangHao on 2016/6/6.
 */
public class CloseUtil {
    public static void closeAll(Closeable ...io){
        for (Closeable tmp : io){
            if (tmp != null){
                try {
                    tmp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void closeSocket(Socket socket){
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeServerSocket(ServerSocket socket){
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
