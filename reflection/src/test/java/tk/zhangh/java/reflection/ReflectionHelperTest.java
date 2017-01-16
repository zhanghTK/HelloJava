package tk.zhangh.java.reflection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.java.reflection.model.Test1;
import tk.zhangh.java.reflection.model.TestBaseClass;
import tk.zhangh.java.reflection.model.TestSubClass;
import tk.zhangh.log.LogAopConf;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

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
    public void test_forName() throws Exception {
        Class<?> clazz = helper.forName("java.util.Date");
        assertEquals(Date.class, clazz);
    }

    @Test
    public void test_forName_with_classLoader() throws Exception {
        Class<?> clazz = helper.forName("tk.zhangh.java.reflection.ReflectionTestConfig", new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String className = name.substring(name.lastIndexOf('.') + 1) + ".class";
                    InputStream inStream = getClass().getResourceAsStream(className);
                    if (inStream == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[inStream.available()];
                    inStream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
        });
        assertEquals(ReflectionTestConfig.class.getName(), clazz.getName());
        assertNotEquals(ReflectionTestConfig.class.getClassLoader(), clazz.getClassLoader());
    }

    @Test
    public void test_newInstance() throws Exception {
        Object object = helper.newInstance(Date.class);
        assertEquals(object.getClass(), Date.class);
    }

    @Test
    public void test_isPublic() throws Exception {
        Class clazz = Test1.class;
        Field publicField = clazz.getDeclaredField("I_INT1");
        assertNotNull(publicField);
        assertTrue(helper.isPublic(publicField));
        Field privateField = clazz.getDeclaredField("I_INT2");
        assertNotNull(privateField);
        assertTrue(!helper.isPublic(privateField));
    }

    @Test
    public void test_accessible_private() throws Exception {
        Class clazz = Test1.class;
        Field privateField = clazz.getDeclaredField("I_INT2");
        assertFalse(privateField.isAccessible());
        privateField = helper.accessible(privateField);
        assertTrue(privateField.isAccessible());
    }

    @Test
    public void test_accessible_public() throws Exception {
        Class clazz = Test1.class;
        Field publicField = clazz.getDeclaredField("I_INT1");
        assertFalse(publicField.isAccessible());
        publicField = helper.accessible(publicField);
        assertFalse(publicField.isAccessible());
    }

    @Test
    public void test_parent_private_field() throws Exception {
        Field field = TestSubClass.class.getSuperclass().getDeclaredField("basePrivateField");
        assertNotNull(helper.field(TestSubClass.class, "basePrivateField"));
        assertEquals(field, helper.field(TestSubClass.class, "basePrivateField"));
    }

    @Test
    public void test_field_with_class() throws Exception {
        assertEquals("SUB_PRIVATE_STATIC_FIELD", helper.fields(TestSubClass.class).get("PRIVATE_STATIC_FIELD"));
    }

    @Test
    public void test_field_with_instance() throws Exception {
        TestSubClass obj = new TestSubClass();
        obj.basePublicField = 1024;
        assertEquals(6, helper.fields(obj).size());
        assertEquals(1000, helper.fields(obj).get("subPrivateField"));
        assertEquals(2333, helper.fields(obj).get("basePrivateField"));
    }

    @Test
    public void test_set_field() throws Exception {
        TestSubClass obj = new TestSubClass();
        helper.set(obj, "basePrivateField", 255);
        assertEquals(255, helper.fields(obj).get("basePrivateField"));
    }

    @Test
    public void test_call_method() throws Exception {
        TestSubClass obj = new TestSubClass();
        Method method = TestSubClass.class.getSuperclass().getDeclaredMethod("privateBaseMethod", Integer.class);
        assertEquals(2333, helper.call(obj, method, 1));
    }

    @Test
    public void test_call_string_name() throws Exception {
        TestSubClass obj = new TestSubClass();
        assertEquals(2333, helper.call(obj, "privateBaseMethod", 1));
    }

    @Test
    public void test_exactMethod() throws Exception {
        Method method = helper.exactMethod(TestSubClass.class, "baseSimilarMethod", ArrayList.class);
        assertEquals(method, TestBaseClass.class.getDeclaredMethod("baseSimilarMethod", ArrayList.class));
    }

    @Test
    public void test_isSimilarSignature() throws Exception {
        Method method = TestSubClass.class.getDeclaredMethod("baseSimilarMethod", List.class);
        assertTrue(helper.isSimilarSignature(method, "baseSimilarMethod", ArrayList.class));
    }

    @Test
    public void test_similarMethod() throws Exception {
        Method method = helper.similarMethod(TestSubClass.class, "baseSimilarMethod", ArrayList.class);
        assertEquals(method, TestSubClass.class.getDeclaredMethod("baseSimilarMethod", List.class));
    }

    @Test
    public void test_types() throws Exception {
        Object[] objects = new Object[]{new Date(), "1234", 1, 1L, 3.14};
        assertEquals(5, helper.types(objects).length);
        assertEquals(Date.class, helper.types(objects)[0]);
        assertEquals(Double.class, helper.types(objects)[objects.length - 1]);
    }

    @Test
    public void test_getMethodName() throws Exception {
        String except = "tk.zhangh.java.reflection.ReflectionHelperTest.test_exactMethod";
        Method method = helper.exactMethod(this.getClass(), "test_exactMethod");
        assertEquals(except, helper.getMethodName(method));
    }
}
