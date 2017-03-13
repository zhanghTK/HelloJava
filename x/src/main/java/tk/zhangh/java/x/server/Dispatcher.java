package tk.zhangh.java.x.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.java.x.server.exception.ServletNotFoundException;

import java.io.IOException;
import java.net.Socket;

/**
 * 分派请求，响应
 * Created by ZhangHao on 2017/2/22.
 */
public class Dispatcher implements Runnable {
    private Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private Socket client;
    private Request request;
    private Response response;
    private int code = 200;

    public Dispatcher(Socket socket) {
        this.client = socket;
        try {
            this.request = new Request(socket);
            this.response = new Response(socket);
        } catch (IOException e) {
            logger.info("init request,response error");
            code = 500;
        }
    }

    @Override
    public void run() {
        Servlet servlet = WebApp.getServlet(request.getUrl());
        try {
            if (servlet == null) {
                logger.info("servlet not found by url:{}", request.getUrl());
                throw new ServletNotFoundException();
            }
            servlet.service(request, response);
            code = 200;
        } catch (ServletNotFoundException e) {
            code = 404;
        } catch (Exception e) {
            logger.error("service has unKnow exception");
            code = 500;
        } finally {
            try {
                response.pushToClient(code);
            } catch (Exception e1) {
                logger.error("push to client error", e1);
            }
        }
        ServerUtils.close(client);
    }
}
