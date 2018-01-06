package tk.zhangh.java.practice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 值传递引用测试
 * Created by ZhangHao on 2017/11/17.
 */
public class ValueOrReference {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }

    // 不能修改基本数据类型的参数
    private static void test1() {
        int value = 10;
        System.out.println("value=" + value);
        edit(value);
        System.out.println("after edit,value=" + value);
    }

    private static int edit(int x) {
        return x * x;
    }

    // 可以修改一个引用数据类型的参数状态
    private static void test2() {
        Person person = new Person(1, "test");
        System.out.println("person=" + person.toString());
        edit(person);
        System.out.println("after edit, value=" + person.toString());
    }

    private static void edit(Person person) {
        person.id = Integer.MAX_VALUE;
        person.name = "NAME";
    }

    @AllArgsConstructor
    @Data
    private static class Person {
        private int id;
        private String name;
    }

    // 不能让参数对象引用一个新对象
    private static void test3() {
        char[] chars1 = {'T', 'E', 'S', 'T', '1'};
        char[] chars2 = {'T', 'E', 'S', 'T', '2'};
        System.out.println("chars1=" + Arrays.toString(chars1) + ", chars=" + Arrays.toString(chars2));
        edit(chars1, chars2);
        System.out.println("after edit, string=" + Arrays.toString(chars1) + ", chars=" + Arrays.toString(chars2));
    }

    private static void edit(char[] chars1, char[] chars2) {
        chars1 = new char[]{'t', 'e', 's', 't'};
        chars2[0] = 't';
    }

    // Integer使用函数交换
    private static void test4() {
        Integer a = 1;
        Integer b = 2;
        System.out.println("a=" + a + ",b=" + b);
        swap(a, b);
        System.out.println("a=" + a + ",b=" + b);
        System.out.println(Integer.valueOf(1));
        System.out.println(Integer.valueOf(2));
    }

    // 注意对缓存的修改
    private static void swap(Integer a, Integer b) {
        int tmp = a.intValue();
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            field.set(a, b);
            field.set(b, new Integer(tmp));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
