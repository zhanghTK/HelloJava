package tk.zhangh.webservice;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import tk.zhangh.webservice.service.HelloWorld;

/**
 * Created by ZhangHao on 2017/8/4.
 */
public class Client {
    public static void main(String[] args) {
        ClientProxyFactoryBean factoryBean = new ClientProxyFactoryBean();
        factoryBean.setServiceClass(HelloWorld.class);
        factoryBean.setAddress("http://localhost:9000/Hello2");
        HelloWorld client = (HelloWorld) factoryBean.create();
        String rsp = client.sayHi();
        System.out.println("return: " + rsp);
    }
}