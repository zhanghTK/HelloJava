package tk.zhangh.java.net.server;

import java.io.IOException;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class LoginServlet extends Servlet{
    @Override
    public void doGet(Request request, Response response) throws IOException {
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        boolean logined = login(name, password);
        if (logined){
            response.println("登录成功!");
        }else {
            response.println("登录失败!");
        }
//        response.println("<html><head><title>欢迎回来</title></head>");
//        response.println("<body><h1>Hello Server</h1>");
//        response.print("欢迎:").print(request.getParameter("username")).print("!");
//        response.println("</body></html>");
    }

    public boolean login(String name, String password)throws IOException{
        return "aaa".equals(name) && "123".equals(password);
    }

    @Override
    public void doPost(Request request, Response response) throws IOException {

    }
}
