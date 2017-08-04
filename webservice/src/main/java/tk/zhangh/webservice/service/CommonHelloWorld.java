package tk.zhangh.webservice.service;

/**
 * Created by ZhangHao on 2017/8/4.
 */
public class CommonHelloWorld implements HelloWorld {

    private String from;
    private String to;

    public CommonHelloWorld(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String sayHi() {
        return from + "->" + to + ":hello";
    }
}
