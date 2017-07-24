package tk.zhangh.java.practice;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义类加载器
 * Created by ZhangHao on 2017/7/24.
 */
public class ClassLoaderTest {
    private static void instanceClassLoader(Object myObj) {
        System.out.println(myObj.getClass());
        System.out.println(myObj.getClass().getClassLoader().getClass().getName());
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        Foo myObj = (Foo) new MyClassLoader().loadClass(Foo.class.getName()).newInstance();
        Object myObj = new MyClassLoader().loadClass(Foo.class.getName()).newInstance();
        Foo obj = new Foo();

        instanceClassLoader(myObj);
        instanceClassLoader(obj);
    }

    public interface Ifoo {

    }

    static class MyClassLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            String className = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream inStream = getClass().getResourceAsStream(className);
            if (inStream == null) {
                return super.loadClass(name);
            }
            try {
                byte[] bytes = new byte[inStream.available()];
                inStream.read(bytes);
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ClassNotFoundException(name);
            }
        }
    }

    public static class Foo implements Ifoo {

    }
}
