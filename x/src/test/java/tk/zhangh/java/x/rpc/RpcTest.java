package tk.zhangh.java.x.rpc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试RPC
 * Created by ZhangHao on 2017/2/22.
 */
public class RpcTest {

    private Consumer consumer;

    @Before
    public void init() throws Exception {
        new Thread(() -> new Provider().start()).start();
        new Thread(() -> {
            consumer = new Consumer();
            consumer.start();
        }).start();
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void call() {
        String result = (String) consumer.call(
                SayHelloServiceImpl.class.getName(),
                "sayHello",
                new Class[]{String.class},
                new String[]{"world"});
        Assert.assertNotNull(result);
        Assert.assertEquals("Hello world", result);
    }
}