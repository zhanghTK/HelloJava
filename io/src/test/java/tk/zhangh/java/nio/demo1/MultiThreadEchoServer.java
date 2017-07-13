package tk.zhangh.java.nio.demo1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO多线程网络服务端
 * Created by ZhangHao on 2017/3/30.
 */
public class MultiThreadEchoServer {
    private static ExecutorService tp = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        ServerSocket echoServer = null;
        Socket clientSocket;
        try {
            echoServer = new ServerSocket(8000);
        } catch (Exception e) {
            System.out.println(e);
        }
        while (true) {
            try {
                clientSocket = echoServer.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
                tp.execute(new HandleMsg(clientSocket));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    static class HandleMsg implements Runnable {
        Socket clientSocket;

        public HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader is = null;
            PrintWriter os = null;
            try {
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(), true);
                String inputLine;
                long b = System.currentTimeMillis();
                while ((inputLine = is.readLine()) != null) {
                    os.println(inputLine);
                }
                long e = System.currentTimeMillis();
                System.out.println("spend:" + (e - b) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (os != null) os.close();
                    clientSocket.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}