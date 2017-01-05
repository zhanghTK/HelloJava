package tk.zhangh.java.entity.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * Created by ZhangHao on 2017/1/3.
 */
@Configuration
public class AnnotationDbTestConfig {
    @Bean
    public EntityDbService dbService() {
        return new EntityDbService();
    }
}
