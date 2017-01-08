package tk.zhangh.java.reflection;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 方法运行时长统计
 * 要求方法所在类必须包含无参数构造方法
 * 1. 针对类，要求有无参构造方法，统计使用@Timer且无参数的方法运行时长
 * 2. 针对方法，统计指定方法的运行时长
 * 统计反射调用执行方法的时长
 * Created by ZhangHao on 2017/1/8.
 */
public class MethodTimer {

    @Autowired
    private ReflectionHelper reflectionHelper;

    /**
     * 打印@Timer注解的方法运行时长
     * 要求：
     * 1. 需要对方法添加@Timer注解
     * 2. 方法不能有参数
     */
    public void printMethodsDuration(Class clazz) {
        Map<String, Long> durations = countMethodsDuration(clazz);
        printFormat(durations);
    }

    /**
     * 统计方法运行时长
     */
    public long getMethodDuration(Method method, Object... args) {
        Class clazz = method.getDeclaringClass();
        Object instance = reflectionHelper.createClassInstance(clazz);
        return countInvokeTime(method, instance, args);
    }

    /**
     * 类中各个方法的执行时长
     *
     * @return Map中key对应方法完全限定名，value对应执行时间
     */
    private Map<String, Long> countMethodsDuration(Class clazz) {
        Map<String, Long> methodsTable = new HashMap<>();
        Method[] methods = clazz.getDeclaredMethods();
        Object object = reflectionHelper.createClassInstance(clazz);
        Arrays.asList(methods).stream()
                .filter(method -> method.isAnnotationPresent(Timer.class))
                .forEach(timeredMethod ->
                        methodsTable.put(
                                reflectionHelper.getMethodName(timeredMethod),
                                countInvokeTime(timeredMethod, object)));
        return methodsTable;
    }

    /**
     * 统计方法运行时长
     *
     * @return 方法运行时长
     */
    private long countInvokeTime(Method method, Object instance, Object... args) {
        long start = System.currentTimeMillis();
        reflectionHelper.invokeMethod(instance, method, args);
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 格式化输出方法运行时长
     *
     * @param duration 类中各个方法的执行时长，key为方法完全限定名，value为运行时长
     */
    private void printFormat(Map<String, Long> duration) {
        for (Map.Entry<String, Long> node : duration.entrySet()) {
            System.out.print(node.getKey() + "执行共耗时：" + node.getValue() + "ms\n");
        }
    }
}
