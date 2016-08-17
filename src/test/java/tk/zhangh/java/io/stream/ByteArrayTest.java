package tk.zhangh.java.io.stream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

/**
 * ByteArrayIn(/Output)Stream测试
 * Created by ZhangHao on 2016/8/17.
 */
public class ByteArrayTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String srcPath = System.getProperty("user.dir") + "/README.md";
    private static final String  README= "# HelloJava\n\n" + "## Learn Java";
    private String destPath = System.getProperty("user.dir") + "/" +String.valueOf(new Date().getTime());
    private String srcMsg = String.valueOf(new Date().getTime());
    @Test
    public void testByteArrayRead() throws Exception{
        byte[] src = srcMsg.getBytes();
        byte[] flush = new byte[1024];
        int length;
        StringBuffer buffer = new StringBuffer();

        try(InputStream inStream = new BufferedInputStream(new ByteArrayInputStream(src))) {
            while (-1 != (length = inStream.read(flush))) {
                buffer.append(new String(flush, 0, length));
            }
        }catch (IOException e) {
            logger.error("IO exception");
            e.printStackTrace();
            throw e;
        }
        Assert.assertEquals(srcMsg, buffer.toString());
    }

    @Test
    public void testByteWrite() throws Exception{
        byte[] src = srcMsg.getBytes();
        byte[] dest;
        try(ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            outStream.write(src, 0, src.length);
            dest = outStream.toByteArray();
        } catch (IOException e) {
            logger.error("IO exception");
            e.printStackTrace();
            throw e;
        }
        Assert.assertEquals(srcMsg, new String(dest));
    }

    @Test
    public void testFile2ByteArray() throws Exception{
        byte[] dest = null;
        byte[] flush = new byte[1024];
        int length;
        try(InputStream inStream = new BufferedInputStream(new FileInputStream(new File(srcPath)));
            ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            while (-1 != (length = (inStream.read(flush)))) {
                outStream.write(flush, 0, length);
            }
            outStream.flush();
            dest = outStream.toByteArray();
        } catch (FileNotFoundException e) {
            logger.error("file not found", e);
            throw e;
        } catch (IOException e) {
            logger.error("close stream error", e);
            throw e;
        }
        String readme = README.replace("\r\n", "\n");
        String destStr = new String(dest).replace("\r\n", "\n");
        Assert.assertEquals(readme, destStr);
    }

    @Test
    public void testByteArray2File() throws Exception{
        File dest = new File(destPath);
        byte[] flush = new byte[1024];
        int length;
        try(BufferedInputStream inStream = new BufferedInputStream(new ByteArrayInputStream(srcMsg.getBytes()));
            OutputStream outStream = new BufferedOutputStream(new FileOutputStream(dest))) {
            while (-1 != (length = inStream.read(flush))) {
                outStream.write(new String(flush, 0, length).getBytes());
            }
        } catch (IOException e) {
            logger.error("testByteArray2File error");
            throw e;
        }
        Thread.sleep(5000);
        Assert.assertEquals(dest.delete(), true);
    }
}
