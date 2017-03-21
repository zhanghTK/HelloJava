package tk.zhangh.java.io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.log.LogAopConf;

import java.io.File;

/**
 * 文件操作帮助类测试
 * Created by ZhangHao on 2017/2/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IoTestConfig.class, LogAopConf.class})
public class FileHelperTest {

    @Autowired
    private FileHelper fileHelper;

    @Test
    public void test_printFileTree() throws Exception {
        File file = new File(this.getClass().getResource("/").getPath());
        fileHelper.printFileTree(file);
    }

    @Test
    public void test_getFileContent() throws Exception {
        File dir = new File(System.getProperty("user.dir") + File.separator + "README.md");
        fileHelper.getFileContent(dir, "utf-8");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_CheckFile_Directory() throws Exception {
        File file = new File(this.getClass().getResource("/").getPath());
        fileHelper.checkFile(file);
    }

    @Test
    public void test_CheckFile() throws Exception {
        File file = new File(System.getProperty("user.dir") + File.separator + "README.md");
        fileHelper.checkFile(file);
    }

    @Test
    public void test_createFileIfNotExists() throws Exception {
        File file = new File(System.getProperty("user.dir") + File.separator + "test");
        fileHelper.createFileIfNotExists(file);
        Assert.assertTrue(file.exists());
    }

    @Test
    public void test_copyFile() throws Exception {
        File src = new File(System.getProperty("user.dir") + File.separator + "README.md");
        File dest = new File(System.getProperty("user.dir") + File.separator + "test");
        fileHelper.copyFile(src, dest);
        Assert.assertTrue(dest.exists());
    }

    @Test
    public void test_copyDir() throws Exception {
        File src = new File(System.getProperty("user.dir") + File.separator + "src");
        File dest = new File(System.getProperty("user.dir") + File.separator + "test-dir");
        fileHelper.copyDir(src, dest);
    }

    @Test
    public void test_deleteDir() throws Exception {
        File src = new File(System.getProperty("user.dir") + File.separator + "src");
        File dest = new File(System.getProperty("user.dir") + File.separator + "test-dir");
        fileHelper.copyDir(src, dest);

        Assert.assertTrue(dest.exists());
        fileHelper.deleteDir(dest);
        Assert.assertFalse(dest.exists());
    }

    @Test
    public void test_reverse() throws Exception {
        File src = new File(System.getProperty("user.dir") + File.separator + "README.md");
        File dest = new File(System.getProperty("user.dir") + File.separator + "test");
        fileHelper.reverse(src, dest);
        Assert.assertFalse(fileHelper.getFileContent(dest, "utf-8").toUpperCase().contains("IO"));
        File tmpFile = new File(System.getProperty("user.dir") + File.separator + "temp");
        fileHelper.reverse(dest, tmpFile);
        Assert.assertTrue(fileHelper.getFileContent(tmpFile, "utf-8").contains("IO"));
    }

    @After
    public void destroy() {
        deleteTestFile("test");
        deleteTestFile("temp");
        deleteTestDir("test-dir");
    }

    private void deleteTestFile(String name) {
        File file = new File(System.getProperty("user.dir") + File.separator + name);
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("删除临时文件" + name + "失败！");
            }
        }
    }

    private void deleteTestDir(String name) {
        File dest = new File(System.getProperty("user.dir") + File.separator + name);
        fileHelper.deleteDir(dest);
        Assert.assertFalse(dest.exists());
    }
}