package tk.zhangh.java.reflection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * Created by ZhangHao on 2017/1/3.
 */
@Configuration
public class ReflectionTestConfig {
    @Bean
    public InstanceAnalyzer instanceAnalyzer() {
        return new InstanceAnalyzer();
    }

    @Bean
    public ClassAnalyzer classAnalyzer() {
        return new ClassAnalyzer();
    }

    @Bean
    public ReflectionHelper reflectionHelper() {
        return new ReflectionHelper();
    }

    @Bean
    public MethodTimer methodTimer() {
        return new MethodTimer();
    }
}
