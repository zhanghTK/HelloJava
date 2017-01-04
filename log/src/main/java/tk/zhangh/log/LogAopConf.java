package tk.zhangh.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 日志切面配置
 * Created by ZhangHao on 2017/1/4.
 */
@EnableAspectJAutoProxy
public class LogAopConf {
    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
