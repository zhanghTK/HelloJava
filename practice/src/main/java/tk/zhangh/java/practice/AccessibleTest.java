package tk.zhangh.java.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 测试反射API中isAccessible方法
 * 更多信息查看AccessibleTest.md
 * Created by ZhangHao on 2017/1/13.
 */
public class AccessibleTest {
    private static Logger logger = LoggerFactory.getLogger(AccessibleTest.class);

    public static void main(String[] args) throws Exception {
        Employee employee = new Employee("1", "ZS");
        logger.info(employee.toString());
        Class<?> clazz = employee.getClass();

        printFieldAccess(clazz, "id");
        printFieldAccess(clazz, "name");

        changeFieldValue(employee, "name");
        changeFieldValue(employee, "id");
    }

    private static void printFieldAccess(Class<?> clazz, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        logger.info("Is '{}' public? {}", field.getName(), Modifier.isPublic(field.getModifiers()));
        logger.info("Is '{}' accessible? {}", field.getName(), field.isAccessible());
    }

    private static void changeFieldValue(Object object, String fieldName) throws NoSuchFieldException {
        Class clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        try {
            field.set(object, "TEST");
            logger.info("changed '{}' field value,{}", fieldName, object);
        } catch (IllegalAccessException e) {
            logger.error("changed '{}' field value fail,by {}", fieldName, e.getClass());
        }
    }
}
