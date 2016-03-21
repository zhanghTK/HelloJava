package tk.zhangh.java.reflection;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangHao on 2016/3/21.
 * 测试反射获取对象信息
 */
public class ObjectAnalyzerTest {

    @Test
    public void testGetObjectInformation() throws Exception {
        String string = "now";
        Date date = new Date();
        Map<String, Date> map = new HashMap<String, Date>();
        map.put(string, date);
        System.out.println(new ObjectAnalyzer().getObjectInfor(string));
        System.out.println(new ObjectAnalyzer().getObjectInfor(2));
        System.out.println(new ObjectAnalyzer().getObjectInfor(map));
    }
}