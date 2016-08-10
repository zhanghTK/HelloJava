package tk.zhangh.java.jvm.load.Initialization;

import org.junit.Test;

/**
 * Created by ZhangHao on 2016/8/10.
 */
public class InitializationAppTest {
    InitializationApp app = new InitializationApp();

    static {
        System.out.println("当虚拟机启动时，用户需要指定一个要执行的主类，虚拟机会先初始化这个主类");
    }

    @Test
    public void testInitialization1() throws Exception {
        app.initialization1();
    }

    @Test
    public void testInitialization2() throws Exception {
        app.initialization2();
    }

    @Test
    public void testInitialization3() throws Exception {
        app.initialization3();
    }

    @Test
    public void testInitialization4() throws Exception {
        app.initialization4();
    }

    @Test
    public void testInitialization5() throws Exception {
    }
}