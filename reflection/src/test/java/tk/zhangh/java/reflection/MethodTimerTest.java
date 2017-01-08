package tk.zhangh.java.reflection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.log.LogAopConf;

import java.util.concurrent.TimeUnit;

/**
 * 方法运行时长统计测试
 * Created by ZhangHao on 2017/1/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LogAopConf.class, ReflectionTestConfig.class})
public class MethodTimerTest {

    @Autowired
    private MethodTimer methodTimer;

    @Timer
    public void run_empty() {
    }

    @Timer
    public void run_100_ms() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Timer
    public void run_print_100_times() {
        for (int i = 0; i < 10000; i++) {
            System.out.print("");
        }
    }

    @Test
    public void test_print_methods_duration() throws Exception {
        methodTimer.printMethodsDuration(MethodTimerTest.class);
    }

    @Test
    public void test_print_method_duration() throws Exception {
        Class<MethodTimerTest> clazz = MethodTimerTest.class;
        long time = methodTimer.getMethodDuration(clazz.getDeclaredMethod("run_100_ms"));
        Assert.assertTrue(time > 100);
        Assert.assertTrue(time < 150);
    }

    @Test
    public void test_method_with_arg() throws Exception {
        Class<ErrorCase> clazz = ErrorCase.class;
        try {
            methodTimer.getMethodDuration(clazz.getDeclaredMethod("methodWithArg"));
            throw new Exception();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    class ErrorCase {
        @Timer
        public void methodWithArg(String arg) {
            System.out.println(arg);
        }
    }
}