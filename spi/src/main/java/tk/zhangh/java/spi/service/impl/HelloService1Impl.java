package tk.zhangh.java.spi.service.impl;

import tk.zhangh.java.spi.service.HelloService;

/**
 * Created by ZhangHao on 2017/7/29.
 */
public class HelloService1Impl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("version 1");
    }
}
