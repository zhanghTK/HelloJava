package tk.zhangh.java.collection.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型数组处理
 * reduce处理一个同步列表，分别使用非泛型与泛型的方式处理
 * Created by ZhangHao on 2016/10/13.
 */
public class GenericArrayTest {
    /**
     * 未使用泛型的函数接口
     */
    interface Function1 {
        Object apply(Object result, Object val);
    }

    /**
     * 未使用泛型的泛型的reduce函数
     * 问题：在同步区域调用外来方法
     * @param list 一个同步列表
     * @param function 函数接口，处理list
     * @param initVal 初始值
     * @return reduce列表后的值
     */
    static Object reduce1(List list, Function1 function, Object initVal) {
        synchronized (list) {
            Object result = initVal;
            for (Object o : list) {
                result = function.apply(result, o);
            }
            return result;
        }
    }

    /**
     * 使用泛型的函数接口
     */
    interface Function2<T> {
        T apply(T result, T val);
    }

    /**
     * 使用泛型的泛型的reduce函数
     * 将同步的List处理成泛型数组，不安全
     * @param list 一个同步列表
     * @param function 函数接口，处理list
     * @param initVal 初始值
     * @return reduce列表后的值
     */
    @SuppressWarnings("unchecked")
    static <E> E reduce2(List<E> list, Function2<E> function, E initVal) {
        E[] snapshot = (E[])list.toArray();
        E result = initVal;
        for (E e : snapshot) {
            result = function.apply(result, e);
        }
        return result;
    }

    /**
     * 使用泛型的泛型的reduce函数
     * 将同步的List拷贝，使用拷贝的List
     * @param list 一个同步列表
     * @param function 函数接口，处理list
     * @param initVal 初始值
     * @return reduce列表后的值
     */
    static <E> E reduce3(List<E> list, Function2<E> function, E initVal) {
        List<E> snapshot;
        synchronized (list) {
            snapshot = new ArrayList<>(list);
        }
        E result = initVal;
        for (E e : snapshot) {
            result = function.apply(result, e);
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        String result = reduce3(list, new Function2<String>() {
            @Override
            public String apply(String result, String val) {
                return result + val;
            }
        }, "");
        System.out.println(result);
    }
}
