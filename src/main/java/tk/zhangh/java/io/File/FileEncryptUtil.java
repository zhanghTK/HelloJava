package tk.zhangh.java.io.file;

import java.io.*;

/**
 * Created by ZhangHao on 2016/8/13.
 */
public class FileEncryptUtil {

    /**
     * 通过取反加密
     * @param src 目标文件
     * @param dest 加密后文件
     * @throws FileNotFoundException 文件不存在
     * @throws IOException IO异常
     */
    public static void reverse(File src, File dest) throws IOException{
        try (InputStream inStream = new FileInputStream(src);
             OutputStream outStream = new FileOutputStream(dest)
        ) {
            int tmp;
            while ((tmp = inStream.read()) != -1) {
                // 取反
                outStream.write(tmp ^ 0xff);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
