package tk.zhangh.java.io.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件帮助类
 * Created by ZhangHao on 2016/4/19.
 */
public class FileHelper {
    private static Logger logger = LoggerFactory.getLogger(FileHelper.class);
    /**
     * 打印某路径下所有文件树结构
     * @param file 目录文件
     */
    public static void printFileTree(File file){
        printFileTreeLevel(file, 0);
    }

    private static void printFileTreeLevel(File file, int level) {
        for (int i = 0; i < level-1; i++) {
            System.out.print("\t");
        }
        System.out.println("├" + file.getName());
        if (file.isDirectory()){
            java.io.File[] files = file.listFiles();
            for (java.io.File fileTmp : files){
                printFileTreeLevel(fileTmp, level + 1);
            }
        }
    }

    /**
     * 递归删除整个文件夹所有内容
     * @param dir 文件夹
     * @return 是否公共
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取字符文件内容
     * @param file 字符文件
     * @param charsetName 字符集
     * @return 文件内容
     * @throws IOException IO异常
     */
    public static String getFileContent(File file, String charsetName) throws IOException{
        checkFile(file);
        StringBuffer buffer = new StringBuffer();
        String line;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName))) {
            while (null != (line = reader.readLine())) {
                buffer.append(line).append("\n");
            }
        }
        return buffer.substring(0, buffer.length() - 1);
    }

    /**
     * 复制文件
     * @param src 原文件
     * @param dest 复制文件
     * @throws IOException IO
     */
    public static void copyFile(final File src, final File dest) throws IOException {
        checkFile(src);
        createFileIfNotExists(dest);
        try(InputStream inStream = new BufferedInputStream(new FileInputStream(src));
            OutputStream outStream = new BufferedOutputStream(new FileOutputStream(dest))) {
            byte[] buffer = new byte[1024];
            int length;
            while (-1 != (length = inStream.read(buffer))) {
                outStream.write(buffer, 0, length);
            }
            outStream.flush();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 复制文件夹
     * @param src 原文件夹
     * @param dest 目标文件夹
     */
    public static void copyDir(File src, File dest) throws IOException{
        if (!src.isDirectory() || ! dest.isDirectory()) {
            logger.error("{} or {} not a directory", src, dest);
            throw new RuntimeException("file must be a directory");
        }
        recursionCopyDir(src, dest);
    }

    private static void recursionCopyDir(File src, File dest)throws IOException{
        if (src.isFile()) {
            try {
                copyFile(src, dest);
            } catch (IOException e) {
                throw e;
            }
        } else if (src.isDirectory()) {
            if (dest.getAbsolutePath().contains(src.getAbsolutePath())) {
                throw new RuntimeException("parent directory can't contains child directory");
            }
            dest.mkdirs();
            for (File file : src.listFiles()) {
                recursionCopyDir(file, new File(dest, file.getName()));
            }
        }
    }

    /**
     * 检查文件，非法文件则抛出运行期异常
     * @param file 文件
     */
    public static void checkFile(File file) {
        if (!file.isFile()) {
            logger.error("{} is not a file", file);
            throw new RuntimeException(file + "is not a file");
        }
    }

    /**
     * 如果不存在则创建文件
     * @param file 文件
     * @throws IOException IO 异常
     */
    public static void createFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            try {
                if (false == file.createNewFile()) {
                    logger.error("{} create fail", file);
                    throw new RuntimeException("create file error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
}
