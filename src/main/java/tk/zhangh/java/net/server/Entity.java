package tk.zhangh.java.net.server;

/**
 *  <servlet>
        <servlet-name>login</servlet-name>
        <servlet-class>tk.zhangh.java.net.server.LoginServlet</servlet-class>
    </servlet>
 * Created by ZhangHao on 2016/6/8.
 */
public class Entity {
    private String name;
    private String clz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }
}
