package tk.zhangh.java.reflection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.log.LogAopConf;

import java.util.Date;

/**
 * 测试运行时类信息分析
 * Created by ZhangHao on 2017/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ReflectionTestConfig.class, LogAopConf.class})
public class ClassAnalyzerTest {
    @Autowired
    private ClassAnalyzer classAnalyzer;

    @Test
    public void test_get_Date_constructors_info() {
        Assert.assertNotNull(classAnalyzer.getConstructorsInfo(Date.class));
    }

    @Test
    public void test_get_Date_fields_info() {
        Assert.assertNotNull(classAnalyzer.getFieldsInfo(Date.class));
    }

    @Test
    public void test_get_Date_methods_info() {
        Assert.assertNotNull(classAnalyzer.getMethodsInfo(Date.class));
    }

    @Test
    public void test_get_Date_info() {
        Assert.assertNotNull(classAnalyzer.getClassInfo(Date.class));
    }
}