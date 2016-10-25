package tk.zhangh.java.common.timer;

import tk.zhangh.java.common.collection.MapUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方法运行时长统计
 *
 * 在无参数方法的待统计方法上使用注解@Timer
 * 使用调用printMethodsDuration传入待统计的类
 *
 * Created by ZhangHao on 2016/10/25.
 */
public class MethodTimerHelper {

    /**
     * 打印各个类中所有@Timer注解方法运行时间
     * @param classes 类型列表
     */
    public void printMethodsDuration(List<Class> classes) {
        for (Class aClass : classes) {
            printMethodsDuration(aClass);
        }
    }

    /**
     * 打印类中所有@Timer注解方法运行时间
     * @param clazz 方法所在类
     */
    public void printMethodsDuration(Class clazz) {
        Map<String, Long> duration = getMethodsDuration(clazz);
        duration = MapUtils.sortByValue(duration);
        printFormat(duration);
    }

    /**
     * 类中各个方法的执行时长
     * @param clazz 方法所在类
     * @return Map中key对应方法完全限定名，value对应执行时间
     */
    private Map<String, Long> getMethodsDuration(Class clazz) {
        Map<String, Long> methodsTable = new HashMap<>();
        Method[] methods = getMethods(clazz);
        Object object = getClassInstance(clazz);
        for (Method method : methods) {
            if (method.isAnnotationPresent(Timer.class)) {
                methodsTable.put(method.getDeclaringClass().getName()+"." +method.getName(), countTime(method, object));
            }
        }
        return methodsTable;
    }

    /**
     * 获得类内声明的所有方法
     * @param clazz 方法所在类
     * @return 方法数组
     */
    private Method[] getMethods(Class clazz) {
        return clazz.getDeclaredMethods();
    }

    /**
     * 调用类的空构造器，获得类对象的实例
     * @param clazz 类对象
     * @return 类的实例
     */
    private Object getClassInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取Class实例错误：" + clazz);
        }
    }

    /**
     * 统计方法运行时长
     * @param method 方法
     * @param object 放在依附的对象
     * @return 方法运行时长
     */
    private long countTime(Method method, Object object) {
        method.setAccessible(true);
        long start = System.currentTimeMillis();
        try {
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("执行方法出错：" + method.toString());
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 格式化输出方法运行时长
     * @param duration 类中各个方法的执行时长，key为方法完全限定名，value为运行时长
     */
    private void printFormat(Map<String, Long> duration) {
        for (Map.Entry<String, Long> node : duration.entrySet()) {
            System.out.print(node.getKey() + "【");
            for (int i = 0; i < node.getValue(); i++) {
                System.out.print("=");
            }
            System.out.println("】执行共耗时：" + node.getValue() + "ms");
        }
    }
}
