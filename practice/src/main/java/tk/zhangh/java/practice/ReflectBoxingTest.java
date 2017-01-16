package tk.zhangh.java.practice;

import java.util.Arrays;

/**
 * 测试自动装箱，拆箱对反射的影响
 * public Method getDeclaredMethod(String name, Class<?>... parameterTypes)
 * 中 parameterTypes 对基本类型和包装类不能自动转换，例如：
 * int.class 与 Integer.class 是不同的
 * Created by ZhangHao on 2017/1/16.
 */
public class ReflectBoxingTest {
    public static void main(String[] args) {
        Class<Employee> clazz = Employee.class;
        getMethod(clazz, "testBaeType", int.class);
        getMethod(clazz, "testBaeType", Integer.class);
        getMethod(clazz, "testBoxingType", int.class);
        getMethod(clazz, "testBoxingType", Integer.class);
    }

    private static void getMethod(Class<?> clazz, String method, Class<?>... classes) {
        try {
            clazz.getDeclaredMethod(method, classes);
        } catch (NoSuchMethodException e) {
            System.out.println("can't get method " + method + "(" + Arrays.toString(classes) + ")");
        }
    }

    private static class Employee {
        public void testBaeType(int x) {
        }

        public void testBoxingType(Integer x) {
        }
    }
}
