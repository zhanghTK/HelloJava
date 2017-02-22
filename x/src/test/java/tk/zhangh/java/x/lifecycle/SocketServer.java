package tk.zhangh.java.x.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器Socket(测试生命周期的Demo,使用生命周期管理socket)
 * Created by ZhangHao on 2016/6/15.
 */
public class SocketServer extends DefaultLifeCycle {
    private static final int port = 8090;
    private static Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private ServerSocket acceptor = null;

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.addLifecycleListener(new LogListenerImp());
        socketServer.init();
        socketServer.start();
        socketServer.suspend();
        socketServer.resume();
        socketServer.destroy();
    }

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
            System.out.println("==========\ndo something with socket\n==========");
            socket = acceptor.accept();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(bufferedReader.readLine());
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

    /**
     * 日志事件监听器
     * 记录声明周期内所有状态
     */
    static class LogListenerImp implements LifecycleListener {
        @Override
        public void lifecycleEvent(LifecycleEvent event) {
            logger.info(event.toString());
        }
    }
}
