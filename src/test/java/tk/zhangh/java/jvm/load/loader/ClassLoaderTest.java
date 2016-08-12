package tk.zhangh.java.jvm.load.loader;

import org.junit.Test;

/**
 * 类加载器测试
 * Created by ZhangHao on 2016/8/12.
 */
public class ClassLoaderTest {
    @Test
    public void getClassLoaders() {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent();
        }
    }

    @Test
    public void getClassPath() {
        String classPath = System.getProperty("java.class.path");
        String[] classPaths = classPath.split(";");
        for (int i = 0; i < classPaths.length; i++) {
            System.out.println(classPaths[i]);
        }
    }
}
