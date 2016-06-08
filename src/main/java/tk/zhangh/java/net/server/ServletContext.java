package tk.zhangh.java.net.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class ServletContext {
    // servletName --> servletClass完整路径
    private Map<String, String> servletMapping;
    // url --> servletName
    private Map<String, String> urlMapping;

    public ServletContext() {
        servletMapping = new HashMap<String, String>();
        urlMapping = new HashMap<String, String>();
    }

    public Map<String, String> getServletMapping() {
        return servletMapping;
    }

    public void setServletMapping(Map<String, String> servletMapping) {
        this.servletMapping = servletMapping;
    }

    public Map<String, String> getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(Map<String, String> urlMapping) {
        this.urlMapping = urlMapping;
    }
}
