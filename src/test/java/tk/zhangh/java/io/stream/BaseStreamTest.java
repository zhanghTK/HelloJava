package tk.zhangh.java.io.stream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

/**
 * 基本输入输出流测试
 * Created by ZhangHao on 2016/8/15.
 */
public class BaseStreamTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testFileInputStream() throws Exception{
        String path = System.getProperty("user.dir")+ "/README.md";
        File file = new File(path);
        InputStream inStream = null;
        Exception exception = null;
        try {
            inStream = new FileInputStream(file);
            byte[] buff = new byte[1024];
            int length;
            while (-1 != (length = inStream.read(buff))) {
                System.out.println(new String(buff, 0, length));
            }
        } catch (FileNotFoundException e) {
            logger.error("文件不存在");
            exception = e;
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("读取文件发生错误");
            e.printStackTrace();
            exception = e;
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                if (exception == null) {
                    logger.error("关闭文件流发生错误");
                    e.printStackTrace();
                }else {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testFileOutputStream() throws Exception{
        String path = System.getProperty("user.dir")+ "/" + new Date().getTime();
        File file = new File(path);
        OutputStream outStream = null;
        Exception exception = null;
        try {
            // true:追加，false:覆盖（默认）
            outStream = new FileOutputStream(file);
            byte[] content = String.valueOf(new Date().getTime()).getBytes();
            outStream.write(content, 0, content.length);
            outStream.flush();
        } catch (FileNotFoundException e) {
            logger.error("文件不存在");
            exception = e;
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("写入文件发生错误");
            exception = e;
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                if (exception == null) {
                    logger.error("关闭文件流发生错误");
                    e.printStackTrace();
                }else {
                    exception.printStackTrace();
                }
            }
        }
        // 手动检查，文件内容与文件名相同
        Thread.sleep(5000);
        Assert.assertTrue(file.delete());
    }

    @Test
    public void testFileReader() throws Exception{
        String path = System.getProperty("user.dir")+ "/README.md";
        File file = new File(path);
        Reader reader = null;
        char[] buffer = new char[1024];
        int length;
        Exception exception = null;

        try {
            reader = new FileReader(file);
            while ((length = reader.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, length));
            }
        }catch (FileNotFoundException e) {
            logger.error("文件不存在");
            exception = e;
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("读取文件发生错误");
            e.printStackTrace();
            exception = e;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                if (exception == null) {
                    logger.error("关闭文件流发生错误");
                    e.printStackTrace();
                }else {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testFileWrite() throws Exception{
        String path = System.getProperty("user.dir")+ "/" + new Date().getTime();
        File file = new File(path);
        Writer writer = null;
        Exception exception = null;
        try {
            // true:追加，false:覆盖（默认）
            writer = new FileWriter(file);
            writer.write(String.valueOf(new Date().getTime()));
            writer.flush();
        } catch (FileNotFoundException e) {
            logger.error("文件不存在");
            exception = e;
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("写入文件发生错误");
            exception = e;
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                if (exception == null) {
                    logger.error("关闭文件流发生错误");
                    e.printStackTrace();
                }else {
                    exception.printStackTrace();
                }
            }
        }
        // 手动检查，文件内容与文件名相同
        Thread.sleep(5000);
        Assert.assertTrue(file.delete());
    }
}
