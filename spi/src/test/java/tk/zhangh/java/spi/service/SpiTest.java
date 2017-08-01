package tk.zhangh.java.spi.service;


import java.util.ServiceLoader;

/**
 * Created by ZhangHao on 2017/7/29.
 */
public class SpiTest {
    public static void main(String[] args) {
        ServiceLoader<HelloService> services = ServiceLoader.load(HelloService.class);

        for (HelloService service : services) {
            service.sayHello();
        }
    }
}