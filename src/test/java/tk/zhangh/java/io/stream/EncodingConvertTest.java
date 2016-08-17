package tk.zhangh.java.io.stream;

import junit.framework.Assert;
import org.junit.Test;

import java.io.*;

/**
 * 编码转码测试
 * Created by ZhangHao on 2016/8/17.
 */
public class EncodingConvertTest {
    private String srcPath = System.getProperty("user.dir") + "/README.md";

    @Test
    public void testEncoding() throws Exception{
        String msg = "你好";
        byte[] data = msg.getBytes();
        Assert.assertEquals(msg, new String(data));
        data = msg.getBytes("GBK");
        Assert.assertNotSame(msg, new String(data));
    }

    @Test
    public void testDecoding() throws Exception{
        String line;
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(srcPath)), "GBK"))) {
            while (null != (line = reader.readLine())) {
                System.out.println(line);
            }
        }catch (IOException e) {
            throw e;
        }
    }
}
