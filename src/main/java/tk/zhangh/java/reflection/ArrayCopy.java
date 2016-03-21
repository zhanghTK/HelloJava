package tk.zhangh.java.reflection;

import java.lang.reflect.Array;

/**
 * Created by ZhangHao on 2016/3/21.
 * ���鿽��ʵ��
 */
public class ArrayCopy {
    /**
     * ���鿽�������������Ͷ�ʧ
     * @param array ����������
     * @param newLength �����������鳤��
     * @return ����������飬���Ͷ�ʧ
     */
    public static Object[] badCopyOf(Object[] array, int newLength){
        Object[] newArray = new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, Math.min(array.length, newLength));
        return newArray;
    }

    /**
     * ���鿽��
     * @param array ����������
     * @param newLength �����������鳤��
     * @return �����������
     */
    public static Object copyOf(Object array, int newLength){
        Class clazz = array.getClass();
        if (!clazz.isArray()){
            return null;
        }
        Class componentType = clazz.getComponentType();  // ��ȡ�������
        int length = Array.getLength(array);  // ԭʼ���鳤��
        Object newArray = Array.newInstance(componentType, newLength);  // ����������
        System.arraycopy(array, 0, newArray, 0, Math.min(length, newLength));  // ����
        return newArray;
    }
}
