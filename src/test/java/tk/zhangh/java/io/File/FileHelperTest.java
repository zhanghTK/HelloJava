package tk.zhangh.java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

/**
 * File帮助类测试
 * Created by ZhangHao on 2016/4/19.
 */
public class FileHelperTest {
    private static final String  README= "# HelloJava\n\n" + "## Learn Java";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String srcPath = System.getProperty("user.dir") + File.separator +"README.md";
    private String destPath = System.getProperty("user.dir") + File.separator + String.valueOf(new Date().getTime());

    @Test
    public void testPrintFile() throws Exception {
        String filePath = System.getProperty("user.dir");
        File file = new File(filePath);
        FileHelper.printFileTree(file);
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

    @Test
    public void testCopyFile() throws Exception {
        File src = new File(srcPath);
        File dest = new File(destPath);
        FileHelper.copyFile(src, dest);
        Assert.assertTrue(dest.exists());
        Thread.sleep(5000);
        Assert.assertTrue(dest.delete());
    }

    @Test
    public void testGetFileContent() throws Exception {
        File file = new File(srcPath);
        String content = FileHelper.getFileContent(file, "utf-8");
        Assert.assertTrue(content.equals(README));
    }

    @Test
    public void testCopyDir() throws Exception {
        // todo 测试复制文件夹
    }

    @Test
    public void testCheckFile() throws Exception {
        Exception exception = null;
        try {
            FileHelper.checkFile(new File("NotFile"));
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        } finally {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testCreateFileIfNotExists() throws Exception {
        File file = new File(destPath);
        FileHelper.createFileIfNotExists(file);
        Assert.assertTrue(file.delete());
    }
}