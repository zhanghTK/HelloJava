package tk.zhangh.java.inner;

/**
 * 内部类实现闭包示例
 * Created by ZhangHao on 2016/8/11.
 * 类似JS代码：
     var adder = function (x) {
     var base = x;
     return function (n) {
     return n + base;
     };
     };

     var add10 = adder(10);
     console.log(add10(5));

     var add20 = adder(20);
     console.log(add20(5));

 */
public class ClosureTest {
    public static void main(String[] args) {
        IAdder myInterface = getAdder(10);
        System.out.println(myInterface.add(1));

        myInterface = getAdder(20);
        System.out.println(myInterface.add(20));
    }

    public static IAdder getAdder(final int base) {
        return new IAdder() {
            @Override
            public int add(int x) {
                return base + x;
            }
        };
    }
}

interface IAdder {
    int add(int x);
}