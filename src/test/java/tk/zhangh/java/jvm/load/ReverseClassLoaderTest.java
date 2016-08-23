package tk.zhangh.java.jvm.load;

import org.junit.Assert;
import org.junit.Test;
import tk.zhangh.java.io.file.FileEncryptUtil;

import java.io.File;

/**
 * Created by ZhangHao on 2016/8/13.
 */
public class ReverseClassLoaderTest {

    @Test
    public void testFindClass() throws Exception {
        // todo 创建文件，复制文件
        File src = new File("D:\\Hello.class");
        File dest = new File("D:\\tmp\\Hello.class");
        FileEncryptUtil.reverse(src, dest);

        ReverseClassLoader loader = new ReverseClassLoader("D:\\tmp");
        Class clazz = loader.loadClass("Hello");
        Assert.assertNotNull(clazz);
    }
}