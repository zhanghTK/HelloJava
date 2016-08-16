package tk.zhangh.java.io.stream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

/**
 * 文件处理流的使用
 * Created by ZhangHao on 2016/8/15.
 */
public class ProcessStreamTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String srcPath = System.getProperty("user.dir") + "/README.md";
    private String destPath = System.getProperty("user.dir") + "/" +String.valueOf(new Date().getTime());

    @Test
    public void testBufferedStreamCopyFile() throws Exception{
        File src = new File(srcPath);
        File dest = new File(destPath);
        if (!src.isFile()) {
            logger.error("{} is not a file", src);
            throw new RuntimeException(src + "is not a file");
        }
        if (!dest.exists()) {
            if (false == dest.createNewFile()) {
                throw new RuntimeException("创建文件失败");
            }
        }
        InputStream inStream = null;
        OutputStream outStream = null;
        Exception exception = null;
        try {
            inStream = new BufferedInputStream(new FileInputStream(src));
            outStream = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            int length;
            while (-1 != (length = inStream.read(buffer))) {
                outStream.write(buffer, 0, length);
            }
            outStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("file not fount", e);
            exception = e;
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("io exception", e);
            exception = e;
            throw e;
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                if (exception == null) {
                    e.printStackTrace();
                }else {
                    exception.printStackTrace();
                }
            }
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                if (exception ==null) {
                    e.printStackTrace();
                }else {
                    exception.printStackTrace();
                }
            }
        }
        Thread.sleep(5000L);
        boolean isSuccess = dest.delete();
        Assert.assertTrue(isSuccess);
    }

    @Test
    public void testBufferedCharCopyFile() throws Exception{
        File src = new File(srcPath);
        File dest = new File(destPath);
        BufferedReader reader = null;
        BufferedWriter writer = null;
        Exception exception = null;
        try {
            reader = new BufferedReader(new FileReader(src));
            writer = new BufferedWriter(new FileWriter(dest));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("file not fount", e);
            exception = e;
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("io exception", e);
            exception = e;
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                if (exception == null) {
                    e.printStackTrace();
                }else {
                    exception.printStackTrace();
                }
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                if (exception ==null) {
                    e.printStackTrace();
                }else {
                    exception.printStackTrace();
                }
            }
        }
        Thread.sleep(5000L);
        boolean isSuccess = dest.delete();
        Assert.assertTrue(isSuccess);
    }

    /**
     * 测试FileInputStream和BufferedInputStream性能差别
     * @throws Exception
     */
    @Test
    public void testReadEfficiency() throws Exception{
        // 替换本地路径
        File file = new File("D:\\迅雷下载\\docker_practice.pdf");
        if (!file.exists()) {
            throw new RuntimeException("请替换本地文件");
        }
        logger.info("读取{}Bit的文件", file.length());
        readWithBite(new FileInputStream(file));
        readWithBite(new BufferedInputStream(new FileInputStream(file)));
        readWithBites(new FileInputStream(file));
        readWithBites(new BufferedInputStream(new FileInputStream(file)));
    }

    private void readWithBite(InputStream inStream) throws Exception{
        long start = System.currentTimeMillis();
        while (-1 != (inStream.read())) {
            // do something
        }
        logger.info("使用{}的read()方法读取耗时{}",
                inStream.getClass().getName(),
                System.currentTimeMillis() - start);
        inStream.close();
    }

    private void readWithBites(InputStream inStream) throws Exception {
        long start = System.currentTimeMillis();
        byte[] buffer = new byte[512];
        while (-1 != (inStream.read(buffer))) {
            // do something
        }
        logger.info("使用{}的read(byte[])方法读取耗时{}",
                inStream.getClass().getName(),
                System.currentTimeMillis() - start);
        inStream.close();
    }
}
