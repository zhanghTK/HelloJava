package tk.zhangh.java.x.server;

import java.io.IOException;

/**
 * servlet
 * Created by ZhangHao on 2016/6/7.
 */
public abstract class Servlet {
    protected void service(Request request, Response response) throws IOException {
        if (HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        }
        if (HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        }
    }

    public void doGet(Request request, Response response) throws IOException {
    }

    public void doPost(Request request, Response response) throws IOException {
    }
}
