package tk.zhangh.java.x.server;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * servlet容器
 * Created by ZhangHao on 2016/6/7.
 */
@Data
public class ServletContext {
    // servletName --> servletClass完整路径
    private Map<String, String> servletMapping = new HashMap<>();
    // url --> servletName
    private Map<String, String> urlMapping = new HashMap<>();
}
