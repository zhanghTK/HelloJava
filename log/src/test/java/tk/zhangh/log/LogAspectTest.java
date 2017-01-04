package tk.zhangh.log;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.java.services.BusinessService;

import java.io.File;

/**
 * 日志切面调用测试
 * Created by ZhangHao on 2017/1/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LogAopConf.class, TestLogAspectConfig.class})
public class LogAspectTest {
    private static final String LOG_PATH = System.getProperty("user.dir") +
            File.separator + "log-repertory" +
            File.separator + "HelloJava.log";

    @Autowired
    private BusinessService service;

    @Test
    public void testLog() {
        File file = new File(LOG_PATH);
        file.deleteOnExit();
        service.sayHello("zhangh");
        Assert.assertTrue(file.exists());
    }
}