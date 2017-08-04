package tk.zhangh.webservice;

import org.apache.cxf.frontend.ServerFactoryBean;
import tk.zhangh.webservice.service.CommonHelloWorld;
import tk.zhangh.webservice.service.HelloWorld;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by ZhangHao on 2017/8/4.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        InputStream inStream = Server.class.getResourceAsStream("/mapping.properties");
        Properties properties = new Properties();
        properties.load(inStream);
        int idx = 1;
        Enumeration<?> keys = properties.propertyNames();
        while (keys.hasMoreElements()) {
            String key = ((String) keys.nextElement());
            String val = ((String) properties.get(key));
            fun(key, val, idx++);
        }
    }

    public static void fun(String from, String to, int idx) {
        HelloWorld helloWorld = new CommonHelloWorld(from, to);
        ServerFactoryBean server = new ServerFactoryBean();
        server.setServiceClass(HelloWorld.class);
        server.setAddress("http://localhost:9000/Hello" + idx);
        server.setServiceBean(helloWorld);
        server.create();
    }
}
