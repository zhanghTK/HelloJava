package tk.zhangh.java.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射帮助类
 * 简化反射调用
 * Created by ZhangHao on 2017/1/8.
 */
public class ReflectionHelper {

    /**
     * 调用类的空构造器，获得类对象的实例
     */
    public Object createClassInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("create " + clazz.getSimpleName() + " instance by default constructor error", e);
        }
    }

    /**
     * 执行方法
     */
    public Object invokeMethod(Object instance, Method method, Object... args) {
        method.setAccessible(true);
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("invoke " + method.getName() + " error:", e);
        }
    }

    /**
     * 获取方法名
     */
    public String getMethodName(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }
}
