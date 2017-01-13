package tk.zhangh.java.reflection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.java.reflection.model.Test1;
import tk.zhangh.log.LogAopConf;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 反射帮助类测试
 * Created by ZhangHao on 2017/1/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LogAopConf.class, ReflectionTestConfig.class})
public class ReflectionHelperTest {
    @Autowired
    private ReflectionHelper helper;

    @Test
    public void test_field_map() throws Exception {
        Test1 test1 = new Test1();

        assertEquals(6, helper.fields(test1).size());
        assertTrue(helper.fields(test1).containsKey("I_INT1"));
        assertTrue(helper.fields(test1).containsKey("S_INT1"));

        assertEquals(3, helper.fields(Test1.class).size());
        assertTrue(helper.fields(test1).containsKey("S_INT1"));

        test1.I_INT1 = 1024;
        assertEquals(1024, helper.fields(test1).get("I_INT1"));
        Test1.S_INT1 = 2048;
        assertEquals(2048, helper.fields(test1).get("S_INT1"));
    }

    @Test
    public void test_is_public() throws Exception {
        Test1 test1 = new Test1();
        Class clazz = test1.getClass();
        Field privateField = clazz.getDeclaredField("S_INT2");
        Field publicField = clazz.getDeclaredField("S_INT1");
        assertTrue(!helper.isPublic(privateField));
        assertTrue(helper.isPublic(publicField));
    }

    @Test
    public void test_accessible() throws Exception {
        Test1 test1 = new Test1();
        Class clazz = test1.getClass();

        Field privateField = clazz.getDeclaredField("S_INT2");
        assertTrue(!privateField.isAccessible());
        privateField = helper.accessible(privateField);
        assertTrue(privateField.isAccessible());

        Field publicField = clazz.getDeclaredField("S_INT1");
        assertTrue(!publicField.isAccessible());
        publicField = helper.accessible(publicField);
        assertTrue(!publicField.isAccessible());
    }

    @Test
    public void test_types() throws Exception {
        Object[] objects = new Object[]{new Date(), "1234", 1, 1L, 3.14};
        assertEquals(5, helper.types(objects).length);
        assertEquals(Date.class, helper.types(objects)[0]);
        assertEquals(Double.class, helper.types(objects)[objects.length - 1]);
    }
}
