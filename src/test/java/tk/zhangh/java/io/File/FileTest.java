package tk.zhangh.java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

/**
 * Created by ZhangHao on 2016/8/15.
 */
public class FileTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testFile() throws Exception{
        String path = this.getClass().getResource("/logback.xml").toString();
        File file = new File(path);
        logger.info("文件分隔符：{}", File.pathSeparator);
        logger.info("路径分隔符：{}", File.separator);  // windows:\ linux:/
        logger.info("存在：{}", file.exists());
        logger.info("可读：{}", file.canRead());
        logger.info("可写：{}", file.canWrite());
        logger.info("可执行：{}", file.canExecute());
        logger.info("是文件夹：{}", file.isDirectory());
        logger.info("是文件：{}", file.isFile());
        logger.info("是绝对路径：{}", file.isAbsolute());
        logger.info("文件长度：{}", file.length());
        logger.info("名称：{}",file.getName());
        logger.info("路径：{}",file.getParent());
        logger.info("绝对路径：{}", file.getAbsolutePath());
        logger.info("上级：{}", file.getParent());
    }

    @Test
    public void testCreateDeleteFile() throws Exception{
        String path = System.getProperty("user.dir") + File.separator + "testCreateDeleteFile";
        System.out.println(path);
        File file = new File(path);
        boolean isSuccess;
        if (!file.exists()) {
            logger.info("创建文件{}", file.getPath());
            isSuccess = file.createNewFile();
            logger.info("创建成功：", isSuccess);
        }
        isSuccess = file.delete();
        logger.info("删除{}成功：{}", file.getPath(), isSuccess);

        logger.info("File.createTempFile()");
        file = File.createTempFile("createTempFile", ".tmp", file.getParentFile());
        file.deleteOnExit();
    }

    @Test
    public void testMkdir() {
        StringBuffer path = new StringBuffer();
        path.append(System.getProperty("user.dir")).
                append(File.separator).
                append("testMkdir-").
                append(new Date().getTime());
        boolean isSuccess;

        File dir = new File(path.toString());
        isSuccess = dir.mkdir();
        logger.info("创建目录成功：{}", isSuccess);

        path.append(File.separator).
                append("parentDir").
                append(File.separator).
                append("childDir");
        File fileChild = new File(path.toString());
        isSuccess = fileChild.mkdirs();
        logger.info("创建多级目录：{}", isSuccess);
        isSuccess = FileHelper.deleteDir(dir);
        logger.info("删除目录：{}", isSuccess);
        Assert.assertTrue(isSuccess);
    }
}
