package tk.zhangh.java.practice;

import lombok.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Java内省机制Demo
 * Created by ZhangHao on 2017/1/19.
 */
public class IntrospectorTest {

    private static final String PROPERTY_NAME = "name";
    private static final String PROPERTY_VALUE = "TEST";
    private UserInfo userInfo;

    private static <T> Map<String, Optional<Object>> beanToMap(T bean) throws Exception {
        Class<?> clazz = bean.getClass();
        Map<String, Optional<Object>> result = new HashMap<>();
        Stream.of(Introspector.getBeanInfo(clazz).getPropertyDescriptors()).forEach(propertyDescriptor -> {
            Optional<Object> value;
            try {
                value = propertyDescriptor.getReadMethod().invoke(bean) == null
                        ? Optional.empty()
                        : Optional.of(propertyDescriptor.getReadMethod().invoke(bean));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            result.put(propertyDescriptor.getName(), value);
        });
        return result;
    }

    @Before
    public void init() throws Exception {
        userInfo = new UserInfo();
    }

    @Test
    public void test_set() throws Exception {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, UserInfo.class);
        Method setName = propertyDescriptor.getWriteMethod();
        setName.invoke(userInfo, PROPERTY_VALUE);
        Assert.assertEquals(PROPERTY_VALUE, userInfo.getName());
    }

    @Test
    public void test_get() throws Exception {
        userInfo.setName(PROPERTY_VALUE);
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, UserInfo.class);
        Method getName = propertyDescriptor.getReadMethod();
        Assert.assertEquals(PROPERTY_VALUE, getName.invoke(userInfo));
    }

    @Test
    public void test_foreach_set() throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        Stream.of(beanInfo.getPropertyDescriptors())
                .filter(propertyDescriptor -> PROPERTY_NAME.equals(propertyDescriptor.getName()))
                .forEach(propertyDescriptor1 -> {
                    Method setMethod = propertyDescriptor1.getWriteMethod();
                    try {
                        setMethod.invoke(userInfo, "TEST");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        Assert.assertEquals(PROPERTY_VALUE, userInfo.getName());
    }

    @Test
    public void test_foreach_get() throws Exception {
        userInfo.setName(PROPERTY_VALUE);
        Stream.of(Introspector.getBeanInfo(UserInfo.class).getPropertyDescriptors())
                .filter(propertyDescriptor -> PROPERTY_NAME.equals(propertyDescriptor.getName()))
                .forEach(propertyDescriptor1 -> {
                    Method getMethod = propertyDescriptor1.getReadMethod();
                    try {
                        Assert.assertEquals(PROPERTY_VALUE, getMethod.invoke(userInfo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    public void test_beanToMap() throws Exception {
        userInfo.setName("test");
        userInfo.setAge(18);
        userInfo.setEmail("xxx");
        userInfo.setId(1L);
        Map<String, Optional<Object>> map = beanToMap(userInfo);
        Assert.assertEquals(userInfo.getName(), map.get("name").get());
        Assert.assertEquals(userInfo.getAge(), map.get("age").get());
        Assert.assertEquals(userInfo.getEmail(), map.get("email").get());
        Assert.assertEquals(userInfo.getId(), map.get("id").get());
    }

    @Data
    class UserInfo {
        private Long id;
        private String name;
        private int age;
        private String email;
    }
}
