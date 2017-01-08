package tk.zhangh.java.office.excel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * Created by ZhangHao on 2017/1/6.
 */
@Configuration
public class ExcelParseTestConfig {
    @Bean
    public CommonExcelParseService commonExcelParseService() {
        return new CommonExcelParseService();
    }

    @Bean
    public CommonExcelParseService commonExcelParseServiceWithDateFormat() {
        CommonExcelParseService service = new CommonExcelParseService();
        service.setDateFormat("yy/M/dd");
        return service;
    }
}
