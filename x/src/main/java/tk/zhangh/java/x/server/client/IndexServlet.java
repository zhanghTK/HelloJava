package tk.zhangh.java.x.server.client;

import tk.zhangh.java.x.server.Request;
import tk.zhangh.java.x.server.Response;
import tk.zhangh.java.x.server.Servlet;

import java.io.IOException;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class IndexServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.println("<html><head><title>Welcome</title></head>");
        response.println("<body><h1>Hello Server</h1>");
        response.println("</body></html>");
    }
}
