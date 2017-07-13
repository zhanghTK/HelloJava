package tk.zhangh.java.nio.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * BIO单线程网络客户端
 * Created by ZhangHao on 2017/7/13.
 */
public class EchoServerClient {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            client = new Socket();
            client.connect(new InetSocketAddress("localhost", 8000));
            writer = new PrintWriter(client.getOutputStream());
            writer.println("Hello!");
            writer.flush();
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("from server: " + reader.readLine());
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (client != null) client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
