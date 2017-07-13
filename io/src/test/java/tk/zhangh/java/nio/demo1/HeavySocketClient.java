package tk.zhangh.java.nio.demo1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * 模拟多线程BIO网络客户端
 * Created by ZhangHao on 2017/3/30.
 */
public class HeavySocketClient {
    private static final int sleep_time = 1000 * 1000 * 1000;
    private static ExecutorService tp = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        EchoClient ec = new EchoClient();
        for (int i = 0; i < 10; i++) {
            tp.execute(ec);
        }
    }

    public static class EchoClient implements Runnable {
        @Override
        public void run() {
            Socket client = null;
            PrintWriter writer = null;
            BufferedReader reader = null;
            try {
                client = new Socket();
                client.connect(new InetSocketAddress("localhost", 8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.write("H");
                LockSupport.parkNanos(sleep_time);
                writer.write("e");
                LockSupport.parkNanos(sleep_time);
                writer.write("l");
                LockSupport.parkNanos(sleep_time);
                writer.write("l");
                LockSupport.parkNanos(sleep_time);
                writer.write("o");
                LockSupport.parkNanos(sleep_time);
                writer.write("!");
                LockSupport.parkNanos(sleep_time);
                writer.println();
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server:" + reader.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) writer.close();
                    if (reader != null) reader.close();
                    if (client != null) client.close();
                } catch (Exception e2) {
                }
            }
        }
    }
}
