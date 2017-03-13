package tk.zhangh.java.x.server;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.java.x.lifecycle.DefaultLifeCycle;
import tk.zhangh.java.x.server.exception.ServerInitException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server主题
 * Created by ZhangHao on 2017/2/22.
 */
public class Server extends DefaultLifeCycle {
    private Logger logger = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;

    @Setter
    private int port = 80;

    private boolean isShutDown = false;

    @Override
    protected void init0() {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("init server socket");
        } catch (IOException e) {
            logger.error("init server socket error");
            throw new ServerInitException("server init error", e);
        }
    }

    @Override
    protected void start0() {
        doRequest();
        logger.info("server start success");
    }

    private void doRequest() {
        while (!isShutDown) {
            try {
                Socket client = serverSocket.accept();
                new Thread(new Dispatcher(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void destroy0() {
        isShutDown = true;
        ServerUtils.close(serverSocket);
        logger.info("server has shut down");
    }
}
