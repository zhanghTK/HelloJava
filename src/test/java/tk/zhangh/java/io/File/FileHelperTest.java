package tk.zhangh.java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * File帮助类测试
 * Created by ZhangHao on 2016/4/19.
 */
public class FileHelperTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testPrintFile() throws Exception {
        String filePath = "D:\\Book";
        File file = new File(filePath);
        System.out.println(filePath);
        FileHelper.printFile(file, 0);
    }

    @Test
    public void testDeleteDir() throws Exception {
        boolean isSuccess;
        String path = System.getProperty("user.dir") + File.separator + "testMkdir";
        File dir = new File(path);
        if (!dir.exists()) {
            isSuccess = dir.mkdir();
            logger.info("创建目录成功：{}", isSuccess);
        }
        isSuccess = FileHelper.deleteDir(dir);
        logger.info("删除目录成功：{}", isSuccess);
        Assert.assertTrue(isSuccess);
    }
}