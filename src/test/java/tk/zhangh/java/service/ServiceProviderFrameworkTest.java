package tk.zhangh.java.service;

import org.junit.Test;

/**
 * 测试服务提供者框架
 * Created by ZhangHao on 2016/10/9.
 */
public class ServiceProviderFrameworkTest {
    @Test
    public void testService() throws Exception {
        Class.forName("tk.zhangh.java.service.EntityProvider");
        Service service = ServiceManager.newInstance("EntityService");
        service.serve();
    }
}