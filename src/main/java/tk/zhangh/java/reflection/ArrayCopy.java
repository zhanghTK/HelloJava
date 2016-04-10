package tk.zhangh.java.reflection;

import java.lang.reflect.Array;

/**
 * Created by ZhangHao on 2016/3/21.
 * 数组拷贝实现
 */
public class ArrayCopy {
    /**
     * 数组拷贝，拷贝后类型丢失
     * @param array 待拷贝数组
     * @param newLength 拷贝后新数组长度
     * @return 拷贝后的数组，类型丢失
     */
    public static Object[] badCopyOf(Object[] array, int newLength){
        Object[] newArray = new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, Math.min(array.length, newLength));
        return newArray;
    }

    /**
     * 数组拷贝
     * @param array 待拷贝数组
     * @param newLength 拷贝后新数组长度
     * @return 拷贝后的数组
     */
    public static Object copyOf(Object array, int newLength){
        Class clazz = array.getClass();
        if (!clazz.isArray()){
            return null;
        }
        Class componentType = clazz.getComponentType();  // 获取数组对象
        int length = Array.getLength(array);  // 原始数组长度
        Object newArray = Array.newInstance(componentType, newLength);  // 创建新数组
        System.arraycopy(array, 0, newArray, 0, Math.min(length, newLength));  // 复制
        return newArray;
    }
}
