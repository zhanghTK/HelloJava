package tk.zhangh.java.x.server.client;

import tk.zhangh.java.x.server.Request;
import tk.zhangh.java.x.server.Response;
import tk.zhangh.java.x.server.Servlet;

import java.io.IOException;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class LoginServlet extends Servlet {
    @Override
    public void doPost(Request request, Response response) throws IOException {
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isLogin = login(name, password);
        if (isLogin) {
            printWelcome(request, response);
        } else {
            printFail(response);
        }

    }

    public boolean login(String name, String password) throws IOException {
        return "zhangh".equals(name) && "zhangh".equals(password);
    }

    private void printWelcome(Request request, Response response) {
        response.println("<html><head><title>欢迎回来</title></head>");
        response.println("<body><h1>Hello Server</h1>");
        response.print("欢迎:").print(request.getParameter("username")).print("!");
        response.println("</body></html>");
    }

    private void printFail(Response response) {
        response.println("<html><head><title>登录失败</title></head>");
        response.println("<body><h1>登录失败</h1>");
        response.println("</body></html>");
    }
}
