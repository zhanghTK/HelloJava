package tk.zhangh.java.reflection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.java.reflection.model.Test1;
import tk.zhangh.java.reflection.model.TestHierarchicalMethodsSubclass;
import tk.zhangh.log.LogAopConf;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

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
        Field privateField = helper.field(TestHierarchicalMethodsSubclass.class, "invisibleField1");
        Field publicField = helper.field(TestHierarchicalMethodsSubclass.class, "visibleField1");
        assertFalse(helper.isPublic(privateField));
        assertTrue(helper.isPublic(publicField));
    }

    @Test
    public void test_static_isPublic() {
        Field privateStaticField = helper.field(TestHierarchicalMethodsSubclass.class, "PRIVATE_RESULT");
        Field publicStaticField = helper.field(TestHierarchicalMethodsSubclass.class, "PUBLIC_RESULT");
        assertTrue(helper.isPublic(publicStaticField));
        assertFalse(helper.isPublic(privateStaticField));
    }

    @Test
    public void test_field() throws Exception {
        Field field = TestHierarchicalMethodsSubclass.class.getField("PUBLIC_RESULT");
        assertEquals(field, helper.field(TestHierarchicalMethodsSubclass.class, "PUBLIC_RESULT"));
    }

    @Test
    public void test_set_field() throws Exception {
        TestHierarchicalMethodsSubclass obj = new TestHierarchicalMethodsSubclass();
        helper.set(obj, "invisibleField3", 255);
        assertEquals(255, obj.getInvisibleField3());
    }

    @Test
    public void test_call_method() throws Exception {
        TestHierarchicalMethodsSubclass obj = new TestHierarchicalMethodsSubclass();
        Method method = obj.getClass().getDeclaredMethod("pub_method", Integer.class);
        assertEquals("PUBLIC_SUB", helper.call(obj, method, 1));
    }

    @Test
    public void test_call_string_name() throws Exception {
        TestHierarchicalMethodsSubclass obj = new TestHierarchicalMethodsSubclass();
        assertEquals("PUBLIC_SUB", helper.call(obj, "pub_method", 1));
    }

    @Test(expected = NoSuchMethodException.class)
    public void test_call_private() throws Exception {
        TestHierarchicalMethodsSubclass obj = new TestHierarchicalMethodsSubclass();
        Method method = obj.getClass().getDeclaredMethod("priv_method", Integer.class);
        helper.call(obj, method, 1);
    }

    @Test(expected = ReflectException.class)
    public void test_call_private_string() throws Exception {
        TestHierarchicalMethodsSubclass obj = new TestHierarchicalMethodsSubclass();
        helper.call(obj, "priv_method", 1);
    }

    // todo
    // exactMethod, similarMethod, getMethodName方法测试，字段，方法继承相关测试
}
