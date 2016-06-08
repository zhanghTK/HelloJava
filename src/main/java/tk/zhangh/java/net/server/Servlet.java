package tk.zhangh.java.net.server;

import java.io.IOException;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public abstract class Servlet {
    protected void service(Request request, Response response)throws IOException{
        doGet(request, response);
        doPost(request, response);
    }

    public abstract void doGet(Request request, Response response) throws IOException;
    public abstract void doPost(Request request, Response response) throws IOException;
}
