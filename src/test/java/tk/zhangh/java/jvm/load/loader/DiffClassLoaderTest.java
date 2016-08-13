package tk.zhangh.java.jvm.load.loader;

import java.io.InputStream;

/**
 * 对比使用不同类加载器加载同一个类
 * Created by ZhangHao on 2016/8/10.
 */
public class DiffClassLoaderTest {
    public static void main(String[] args) throws Exception{
        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String className = name.substring(name.lastIndexOf('.') + 1) + ".class";
                    InputStream iStream = getClass().getResourceAsStream(className);
                    if (iStream == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[iStream.available()];
                    iStream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object myObj = loader.loadClass(Foo.class.getName()).newInstance();
        Object obj = new Foo();

        System.out.println("\n--------------------自定义------------------------");
        System.out.println(myObj.getClass());
        System.out.println("加载器：" + myObj.getClass().getClassLoader().getClass().getName());
        System.out.println("myObj instanceof Foo " + (myObj instanceof Foo));

        System.out.println("\n-------------------默认类加载器-------------------------");
        System.out.println(obj.getClass());
        System.out.println("加载器：" + obj.getClass().getClassLoader().getClass().getName());
        System.out.println("obj instanceof Foo " + (obj instanceof Foo));

    }
}
