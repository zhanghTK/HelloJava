package tk.zhangh.java.inner;

/**
 * Created by ZhangHao on 2016/8/22.
 */
public class InnerTest {
    String field = "InnerTest.field";
    static String staticField = "InnerTest.staticField";

    // 匿名成员内部类定义
    protected Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    // 静态内部类定义
    protected static class StaticInnerClass {
        private static String staticField;  // 静态内部类可以定义静态属性
        private String field;  // 静态内部类可以定义非静态属性

        public void useField() {
            System.out.println(InnerTest.staticField);
            System.out.println(staticField);
            System.out.println(field);
//            System.out.println(InnerTest.field);  // 静态内部类不能访问外部类的非静态成员
        }
    }

    protected class FieldInnerClass {
//        private static String staticField = "staticField";  // 成员内部类不可包含普通静态属性
        private static final String staticFinalField = "staticFinalField";  // 成员内部类可以包含编译阶段确定的常量
//        private static final Date DATE = new Date();  // 成员内部类不可包含编译阶段不确定的常量（脱离了外部类控制？）
        private String field = "FieldInnerClass.field";

        public void useField() {
            System.out.println(staticField);
            System.out.println(InnerTest.this.field);
            System.out.println(field);

        }
    }

    private FieldInnerClass fieldInnerClass = new FieldInnerClass();  // 在外部类内直接创建成员内部类对象

    // 使用局部内部类
    private void testLocalInnerClass() {
        String localInnerClassStr = "localInnerClassStr";
        final String localInnerClassFinalStr = "localInnerClassFinalStr";

        // 局部内部类
        // 不能修饰局部变量的修饰符也不能修饰局部内部类
        class LocalInnerClass {
            String field = "LocalInnerClass.field";
//            static String staticField = "LocalInnerClass.staticField";  // 局部内部类不能定义静态属性

            public void useField() {
                System.out.println(staticField);
                System.out.println(InnerTest.this.field);
                System.out.println(field);
//                 局部内部类不能引用方法的普通局部变量
//                System.out.println(localInnerClassStr);
//                局部内部类可以引用方法的静态局部变量
//                方法局部变量的生命周期与局部内部类的生命周期不一致
                System.out.println(localInnerClassFinalStr);
            }
        }

        // 匿名局部内部类（接口式）
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        // 匿名局部内部类（继承式）
        Exception exception = new RuntimeException() {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }
}

class OtherClass {
    InnerTest.StaticInnerClass staticInnerClass = new InnerTest.StaticInnerClass();  // 第三方类类创建静态内部类
    InnerTest.FieldInnerClass fieldInnerClass = new InnerTest().new FieldInnerClass();  // 第三方类创建成员内部类
}