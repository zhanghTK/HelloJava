package tk.zhangh.log;

import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import tk.zhangh.java.services.BusinessService;

/**
 * 业务Bean配置
 * Created by ZhangHao on 2017/1/4.
 */
@ContextConfiguration
public class TestLogAspectConfig {
    @Bean
    public BusinessService businessService() {
        return new BusinessService();
    }
}
