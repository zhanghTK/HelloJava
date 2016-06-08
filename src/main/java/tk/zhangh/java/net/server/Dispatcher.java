package tk.zhangh.java.net.server;

import java.net.Socket;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class Dispatcher implements Runnable{
    private Socket client;
    private Request request;
    private Response response;
    private int code = 200;

    public Dispatcher(Socket socket) {
        this.client = socket;
        try {
            this.request = new Request(socket);
            this.response = new Response(socket);
        } catch (Exception e) {
            code = 500;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Servlet servlet = WebApp.getServlet(request.getUrl());
            if (servlet == null){
                code = 404;
            }else {
                servlet.service(request, response);
            }
            response.pushToClient(code);
        } catch (Exception e) {
            e.printStackTrace();
            code = 500;
            try {
                response.pushToClient(code);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        CloseUtil.closeSocket(client);
    }
}
