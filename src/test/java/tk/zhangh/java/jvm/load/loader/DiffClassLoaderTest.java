package tk.zhangh.java.jvm.load.loader;

import java.io.InputStream;

/**
 * 对比使用不同类加载器加载同一个类
 * Created by ZhangHao on 2016/8/10.
 */
public class DiffClassLoaderTest {
    public static void main(String[] args) throws Exception{
        ClassLoader loader1 = new ClassLoader() {
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

        Object obj = new DiffClassLoaderTest();
        Object myObj = loader1.loadClass(DiffClassLoaderTest.class.getName()).newInstance();

        System.out.println("\n-------------------默认类加载器-------------------------");
        System.out.println(obj.getClass());
        System.out.print("instance of DiffClassLoaderTest: ");
        System.out.println(obj instanceof DiffClassLoaderTest);

        System.out.println("\n--------------------自定义------------------------");
        System.out.println(myObj.getClass());
        System.out.print("instance of DiffClassLoaderTest: ");
        System.out.println(myObj instanceof DiffClassLoaderTest);

    }
}