package tk.zhangh.java.reflection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.log.LogAopConf;

import java.util.*;

import static org.junit.Assert.assertTrue;

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
        String data = "java.util.Date[fastTime";
        Date date = new Date(new Random(System.currentTimeMillis()).nextInt());
        assertTrue(instanceAnalyzer.getInstanceInfo(date).contains(data));
    }

    @Test
    public void test_get_num_info() {
        Integer num = 2;
        String data = "java.lang.Integer[value = 2][][]";
        assertTrue(instanceAnalyzer.getInstanceInfo(num).contains(data));
    }

    @Test
    public void test_get_array_info() {
        int[] array = new int[]{1, 2, 3, 4, 5};
        String data = "int[]{1,2,3,4,5}";
        assertTrue(instanceAnalyzer.getInstanceInfo(array).contains(data));
    }

    @Test
    public void test_get_map_info() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "one");
        map.put("2", "two");
        String data = "java.util.HashMap";
        assertTrue(instanceAnalyzer.getInstanceInfo(map).contains(data));
    }

    @Test
    public void test_get_list_info() {
        List<String> list = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3"}));
        String data = "{1,2,3}";
        assertTrue(instanceAnalyzer.getInstanceInfo(list).contains(data));
    }
}
