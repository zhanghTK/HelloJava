package tk.zhangh.java.io;

import org.junit.Test;

import java.io.File;

/**
 * File帮助类测试
 * Created by ZhangHao on 2016/4/19.
 */
public class FileHelperTest {

    @Test
    public void testPrintFile() throws Exception {
        String filePath = "D:\\Book";
        File file = new File(filePath);
        System.out.println(filePath);
        new FileHelper().printFile(file, 0);
    }
}