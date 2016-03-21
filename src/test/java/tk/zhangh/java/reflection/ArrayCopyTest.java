package tk.zhangh.java.reflection;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by ZhangHao on 2016/3/21.
 * �������鿽��
 */
public class ArrayCopyTest {

    /**
     * �������鿽�������Ͷ�ʧ
     */
    @Test
    public void testBadCopyOf(){
        Object[] array = {1, 2, 3};
        Object[] newArray = ArrayCopy.badCopyOf(array, 10);
        System.out.println(Arrays.toString(newArray));
    }

    /**
     * �������鿽��
     */
    @Test
    public void testCopyOf(){
        int[] array = {1, 2, 3};
        int[] newArray = (int[]) ArrayCopy.copyOf(array, 10);
        System.out.println(Arrays.toString(newArray));
    }
}