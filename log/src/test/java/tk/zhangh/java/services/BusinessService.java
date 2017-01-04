package tk.zhangh.java.services;

import org.springframework.stereotype.Component;

/**
 * 业务service
 * 测试使用AOP记录调用前后日志
 * Created by ZhangHao on 2017/1/4.
 */
@Component
public class BusinessService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
