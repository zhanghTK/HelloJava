package tk.zhangh.java.reflection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.log.LogAopConf;

import java.util.*;

/**
 * 测试运行时对象信息分析
 * Created by ZhangHao on 2017/1/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ReflectionTestConfig.class, LogAopConf.class})
public class InstanceAnalyzerTest {
    @Autowired
    private InstanceAnalyzer instanceAnalyzer;

    @Test
    public void test_get_date_info() {
        int time = new Random(System.currentTimeMillis()).nextInt();
        Date date = new Date(time);
        Assert.assertNotNull(instanceAnalyzer.getInstanceInfo(date));
    }

    @Test
    public void test_get_num_info() {
        Integer num = 2;
        Assert.assertNotNull(instanceAnalyzer.getInstanceInfo(num));
    }

    @Test
    public void test_get_array_info() {
        int[] array = new int[]{1, 2, 3, 4, 5};
        Assert.assertNotNull(instanceAnalyzer.getInstanceInfo(array));
    }

    @Test
    public void test_get_map_info() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "one");
        map.put("2", "two");
        Assert.assertNotNull(instanceAnalyzer.getInstanceInfo(map));
    }

    @Test
    public void test_get_list_info() {
        List<String> list = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3"}));
        Assert.assertNotNull(instanceAnalyzer.getInstanceInfo(list));
    }
}
