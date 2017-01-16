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

    /**
     * 使用当前类加载器加载一个类
     */
    public Class<?> forName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 使用指定加载器根据全类名加载一个类
     */
    public Class<?> forName(String name, ClassLoader classLoader) {
        try {
            return Class.forName(name, true, classLoader);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }


    /**
     * 调用类的空构造器，获得类对象的实例
     */
    public Object newInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReflectException("create " + clazz.getSimpleName() + " instance by default constructor error", e);
        }
    }

    /**
     * 判断一个Member是否是公开的成员
     * Member：Method，Constructor,Field等
     */
    public <T extends Member> boolean isPublic(T accessible) {
        return Modifier.isPublic(accessible.getModifiers()) && Modifier.isPublic(accessible.getDeclaringClass().getModifiers());
    }

    /**
     * 使一个AccessibleObject对象可以访问
     */
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

    /**
     * 获取类的指定字段
     */
    public Field field(Class clazz, String name) {
        try {
            return accessible(clazz.getField(name));
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

    /**
     * 获取类的静态字段以及对应值
     * 包括继承体系中的父类，各个访问权限的字段
     */
    public Map<String, Object> fields(Class clazz) {
        Map<String, Object> result = new LinkedHashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();
                    if (!result.containsKey(name)) {
                        try {
                            result.put(name, field(clazz, name).get(null));
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

    /**
     * 获取一个类的所有字段以及对应值
     * 包括继承体系中的父类，各个访问权限的字段，静态字段以及对象字段
     */
    public Map<String, Object> fields(Object instance) {
        Class<?> clazz = instance.getClass();
        Map<String, Object> result = new LinkedHashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                String name = field.getName();
                if (!result.containsKey(name)) {
                    try {
                        result.put(name, field(clazz, name).get(instance));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    /**
     * 给对象的字段设置值
     */
    public void set(Object instance, String name, Object value) {
        Field field = field(instance.getClass(), name);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new ReflectException();
        }
    }

    /**
     * 执行指定方法
     */
    public Object call(Object instance, Method method, Object... args) {
        accessible(method);
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("invoke " + method.getName() + " error:", e);
        }
    }

    /**
     * 执行指定签名的方法
     */
    public Object call(Object instance, String methodName, Object... args) {
        Class<?>[] classes = types(args);
        Method method;
        try {
            method = exactMethod(instance.getClass(), methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = similarMethod(instance.getClass(), methodName, classes);
                return call(instance, method, args);
            } catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
        return call(instance, method, args);
    }

    /**
     * 根据方法签名准确匹配一个方法
     */
    public Method exactMethod(Class<?> clazz, String name, Class<?>... classes) throws NoSuchMethodException {
        try {
            return clazz.getMethod(name, classes);
        } catch (NoSuchMethodException e) {
            while (clazz != null) {
                try {
                    return clazz.getDeclaredMethod(name, classes);
                } catch (NoSuchMethodException ignore) {
                }

                clazz = clazz.getSuperclass();
            }
            throw new NoSuchMethodException();
        }
    }

    /**
     * 根据方法签名匹配一个相似方法
     */
    public Method similarMethod(Class<?> clazz, String name, Class<?>... classes) throws NoSuchMethodException {
        for (Method method : clazz.getMethods()) {
            if (isSimilarSignature(method, name, classes)) {
                return method;
            }
        }
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (isSimilarSignature(method, name, classes)) {
                    return method;
                }
            }
            clazz = clazz.getSuperclass();
        }
        throw new NoSuchMethodException();
    }

    /**
     * 批量获取对象类型信息
     */
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
     * 获取方法名
     * 类群路径.方法名
     */
    public String getMethodName(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    /**
     * 判断方法签名是否匹配
     */
    public boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>... desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    /**
     * 判断方法参数类型使用匹配
     */
    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length != actualTypes.length)
            return false;
        for (int i = 0; i < actualTypes.length; i++) {
            if (actualTypes[i] == null)
                continue;
            if (declaredTypes[i].isAssignableFrom(actualTypes[i]))
                continue;
            return false;
        }
        return true;
    }


}
