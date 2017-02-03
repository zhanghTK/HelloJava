package tk.zhangh.java.io;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IO模块测试配置
 * Created by ZhangHao on 2017/2/3.
 */
@Configuration
public class IoTestConfig {
    @Bean
    public FileHelper fileHelper() {
        return new FileHelper();
    }
}
