package tk.zhangh.java.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 反射帮助类
 * 简化反射调用
 * Created by ZhangHao on 2017/1/8.
 */
public class ReflectionHelper {
    private Logger logger = LoggerFactory.getLogger(ReflectionHelper.class);

    private Field field0(Class clazz, String name) {
        try {
            return accessible(clazz.getField(name));  // 获取public字段
        } catch (NoSuchFieldException e) {
            while (clazz != null) {
                try {
                    return accessible(clazz.getDeclaredField(name));
                } catch (NoSuchFieldException ignored) {
                }
                clazz = clazz.getSuperclass();
            }
            throw new ReflectException(e);
        }
    }

    public Map<String, Object> fields(Class clazz) {
        Map<String, Object> result = new LinkedHashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();
                    if (!result.containsKey(name)) {
                        try {
                            result.put(name, field0(clazz, name).get(null));
                        } catch (IllegalAccessException e) {
                            throw new ReflectException(e);
                        }
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    public Map<String, Object> fields(Object instance) {
        Class<?> clazz = instance.getClass();
        Map<String, Object> result = new LinkedHashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                String name = field.getName();
                if (!result.containsKey(name)) {
                    try {
                        result.put(name, field0(clazz, name).get(instance));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }


    public <T extends Member> boolean isPublic(T accessible) {
        return Modifier.isPublic(accessible.getModifiers()) && Modifier.isPublic(accessible.getDeclaringClass().getModifiers());
    }

    public <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) {
            return null;
        }
        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (isPublic(member)) {
                return accessible;
            }
        }
        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }
        return accessible;
    }

    public Class<?>[] types(Object... values) {
        if (values == null) {
            return new Class[0];
        }
        Class<?>[] result = new Class[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] == null ? null : values[i].getClass();
        }
        return result;
    }

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
