package tk.zhangh.java.net.server;

import java.io.IOException;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class RegisterServlet extends Servlet{
    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.println("<html><head><title>返回注册</title></head>");
        response.println("<body><h1>Hello Server</h1>");
        response.print("你的用户名为：").print(request.getParameter("username")).print("!");
        response.println("</body></html>");
    }

    @Override
    public void doPost(Request request, Response response) throws IOException {

    }
}
