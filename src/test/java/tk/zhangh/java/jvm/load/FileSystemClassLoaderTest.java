package tk.zhangh.java.jvm.load;

import org.junit.Assert;
import org.junit.Test;

/**
 * 自定义系统文件类加载器测试
 * Created by ZhangHao on 2016/8/12.
 */
public class FileSystemClassLoaderTest {

    @Test
    public void testFindClass() throws Exception {
        // todo 复制包、类，使用FileSystemClassLoader加载
        FileSystemClassLoader loader = new FileSystemClassLoader("D:");
        Class clazz = loader.loadClass("Hello");
        System.out.println(clazz);
    }

    @Test
    public void testDiffClassLoader() throws Exception{
        FileSystemClassLoader loader1 = new FileSystemClassLoader("D:");
        FileSystemClassLoader loader2 = new FileSystemClassLoader("D:");
        Class clazz1 = loader1.loadClass("Hello");
        Class clazz1_1 = loader1.loadClass("Hello");
        Class clazz2 =loader2.loadClass("Hello");
        Class strClazz = loader1.loadClass("java.lang.String");
        Class thisClazz = loader1.loadClass(this.getClass().getName());
        // 使用自定义加载器加载类
        Assert.assertEquals(clazz1.getClassLoader().getClass(), FileSystemClassLoader.class);
        // 遵循双亲委派机制的类加载器加载String，实际被BootstrapLoader加载
        Assert.assertNull(strClazz.getClassLoader());
        // 遵循双亲委派机制的类加载器加载classpath下的自定义类，实际被sun.misc.Launcher$AppClassLoader加载
        Assert.assertEquals(thisClazz.getClassLoader().getClass().getName(), "sun.misc.Launcher$AppClassLoader");
        // 使用同一个类加载器加载同一个类，产生一样的Class
        Assert.assertTrue(clazz1.hashCode() == clazz1_1.hashCode());
        // 使用不一个类加载器加载同一个类，产生不同的Class
        Assert.assertTrue(clazz1.hashCode() != clazz2.hashCode());
    }
}