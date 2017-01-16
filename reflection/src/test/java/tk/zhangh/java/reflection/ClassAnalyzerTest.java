package tk.zhangh.java.reflection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.log.LogAopConf;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * 测试运行时类信息分析
 * Created by ZhangHao on 2017/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ReflectionTestConfig.class, LogAopConf.class})
public class ClassAnalyzerTest {

    private static final String constructor = "public java.util.Date(int, int, int, int, int, int);";
    private static final String field = "private static final BaseCalendar gcal";
    private static final String method = "public boolean after(java.util.Date);";


    @Autowired
    private ClassAnalyzer classAnalyzer;

    @Test
    public void test_get_Date_constructors_info() {
        assertTrue(classAnalyzer.getConstructorsInfo(Date.class).contains(constructor));
    }

    @Test
    public void test_get_Date_fields_info() {
        assertTrue(classAnalyzer.getFieldsInfo(Date.class).contains(field));
    }

    @Test
    public void test_get_Date_methods_info() {
        assertTrue(classAnalyzer.getMethodsInfo(Date.class).contains(method));
    }

    @Test
    public void test_get_Date_info() {
        assertTrue(classAnalyzer.getClassInfo(Date.class).contains(constructor));
        assertTrue(classAnalyzer.getClassInfo(Date.class).contains(field));
        assertTrue(classAnalyzer.getClassInfo(Date.class).contains(method));
    }
}