package tk.zhangh.java.practice;

import java.util.List;

/**
 * 生产者使用extends
 * 消费者使用super
 * 既要生产又要消费，不要使用通配符声明
 * <p>
 * 一切的一切都是因为泛型擦除，泛型不能协变
 * <p>
 * <? extends T> 声明的类型是T的子类，具体哪个子类不知道，所以不能随便给？赋引用，因为可能出现转型错误，但是知道是T的子类，肯定实现了T的方法
 * <? super T>   声明的类型是T的父类，具体哪个父类不知道，反正至少T类型，所以T的子类型都可以被？引用，但引用的是什么类型不知道，所以根据？获取的都是object
 * <p>
 * Created by ZhangHao on 2017/3/21.
 */
public class GenericTest {

    private void extendsFun(List<? extends Number> list) {
        list.get(0);
//        list.add(new Object());
    }

    private void superFun(List<? super Number> list) {
        Object object = list.get(0);
        list.add(1);
    }
}
