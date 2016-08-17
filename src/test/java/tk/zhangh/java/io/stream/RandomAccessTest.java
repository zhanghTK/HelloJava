package tk.zhangh.java.io.stream;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 测试文件随机访问
 * Created by ZhangHao on 2016/8/17.
 */
public class RandomAccessTest {
    private String srcPath = System.getProperty("user.dir") + "/README.md";

    @Test
    public void testRandomAccess() throws Exception {
        File file = new File(srcPath);
        byte[] flush = new byte[1024];
        int length;
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            randomAccessFile.seek(11);
            while (-1 != (length = randomAccessFile.read(flush))) {
                if (length > 21) {
                    System.out.println(new String(flush, 0, 21));
                    break;
                }else {
                    System.out.println(new String(flush, 0, length));
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
