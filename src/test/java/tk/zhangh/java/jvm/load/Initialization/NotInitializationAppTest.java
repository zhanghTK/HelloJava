package tk.zhangh.java.jvm.load.Initialization;

import org.junit.Test;

/**
 * Created by ZhangHao on 2016/8/10.
 */
public class NotInitializationAppTest {
    NotInitializationApp app = new NotInitializationApp();

    @Test
    public void testNotInitialization1() throws Exception {
        app.notInitialization1();
    }

    @Test
    public void testNotInitialization2() throws Exception {
        app.notInitialization2();
    }

    @Test
    public void testNotInitialization3() throws Exception {
        app.notInitialization3();
    }
}