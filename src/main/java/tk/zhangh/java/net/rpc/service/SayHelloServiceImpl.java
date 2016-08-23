package tk.zhangh.java.net.rpc.service;

/**
 * Created by ZhangHao on 2016/8/22.
 */
public class SayHelloServiceImpl implements SayHelloService{
    @Override
    public String sayHello(String arg) {
        return "Hello " + arg;
    }
}
