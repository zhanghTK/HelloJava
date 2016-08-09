package tk.zhangh.java.lifecycle;

import tk.zhangh.java.exception.LifecycleException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器Socket(测试生命周期的Demo,使用生命周期管理socket)
 * Created by ZhangHao on 2016/6/15.
 */
public class SocketServer extends DefaultLifeCycle {
    private ServerSocket acceptor = null;
    private int port = 8090;

    @Override
    protected void init0() throws LifecycleException {
        try {
            acceptor = new ServerSocket(port);
        } catch (IOException e) {
            throw new LifecycleException(e);
        }
    }

    @Override
    protected void start0() throws LifecycleException {
        Socket socket = null;
        try {
            System.out.println("==========");
            System.out.println("do something with socket");
            System.out.println("==========");
//            socket = acceptor.accept();
//            InputStream inputStream = socket.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            System.out.println(bufferedReader.readLine());
        } catch (Exception e) {
            throw new LifecycleException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void destroy0() throws LifecycleException {
        if (acceptor != null) {
            try {
                acceptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
