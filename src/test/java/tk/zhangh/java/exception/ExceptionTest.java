package tk.zhangh.java.exception;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 异常栈覆盖
 * Created by ZhangHao on 2016/8/22.
 */
public class ExceptionTest {
    private static final String FILE_PATH = System.getProperty("user.dir")+ "/README.md";

    @Test
    public void testExceptionStackTrace() throws Exception{
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(FILE_PATH));
            // do something with inStream
            throw new IOException("read file error");
        } catch (IOException e) {
            throw e;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                    throw new IOException("close error");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
