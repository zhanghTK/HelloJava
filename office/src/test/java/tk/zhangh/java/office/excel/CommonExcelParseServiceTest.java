package tk.zhangh.java.office.excel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.log.LogAopConf;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 通用Excel解析测试
 * Created by ZhangHao on 2017/1/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LogAopConf.class, ExcelParseTestConfig.class})
public class CommonExcelParseServiceTest {

    @Qualifier("commonExcelParseService")
    @Autowired
    ExcelParseService excelParseService;

    @Autowired
    @Qualifier("commonExcelParseServiceWithDateFormat")
    ExcelParseService commonWithDateFormat;

    @Test
    public void test_parse_excel() throws Exception {
        File file = new File("src/test/resources/common.xlsx");
        List<Map<String, String>> excel = excelParseService.convertToList(file, 0);
        Assert.assertEquals(9, excel.size());
        Assert.assertEquals("2016-09-09 00:00", excel.get(excel.size() - 1).get("日期"));
    }

    @Test
    public void test_parse_excel_by_inStream() throws Exception {
        InputStream inStream = this.getClass().getResourceAsStream("/common.xlsx");
        List<Map<String, String>> excel = excelParseService.convertToList(inStream, "common.xlsx", 0);
        Assert.assertEquals(9, excel.size());
        Assert.assertEquals("2016-09-09 00:00", excel.get(excel.size() - 1).get("日期"));
    }

    @Test
    public void test_parse_excel_with_special_date() throws Exception {
        File file = new File("src/test/resources/common.xlsx");
        List<Map<String, String>> excel = commonWithDateFormat.convertToList(file, 1);
        Assert.assertEquals(9, excel.size());
        Assert.assertEquals("16/9/09", excel.get(excel.size() - 1).get("日期"));
    }

    @Test
    public void test_parse_empty_field() throws Exception {
        File file = new File("src/test/resources/common.xlsx");
        List<Map<String, String>> excel = commonWithDateFormat.convertToList(file, 0);
        Assert.assertEquals(9, excel.size());
        Assert.assertEquals("16/9/09", excel.get(excel.size() - 1).get("日期"));
        Assert.assertEquals("", excel.get(1).get("第九列"));
    }
}