package tk.zhangh.java.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 文件操作帮助类
 * Created by ZhangHao on 2017/2/3.
 */
@Component
public class FileHelper {
    private static Logger logger = LoggerFactory.getLogger(FileHelper.class);

    /**
     * 检查文件是否有效
     */
    public void checkFile(File file) {
        if (!file.isFile()) {
            logger.error("{} isn't a file", file);
            throw new IllegalArgumentException(file + "is not a file");
        }
    }

    /**
     * 打印某路径下所有文件树结构
     */
    public void printFileTree(File file) {
        printFileTree(file, 0);
    }

    private void printFileTree(File file, int level) {
        for (int i = 0; i < level - 1; i++) {
            System.out.print("\t");
        }
        System.out.println("├" + file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File aFile : files) {
                printFileTree(aFile, level + 1);
            }
        }
    }

    /**
     * 获取字符文件内容
     */
    public String getFileContent(File file, String charsetName) throws IOException {
        checkFile(file);
        StringBuilder buffer = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName))) {
            while (null != (line = reader.readLine())) {
                buffer.append(line).append("\n");
            }
        }
        return buffer.substring(0, buffer.length() - 1);
    }

    /**
     * 复制文件
     */
    public void copyFile(File src, File dest) throws IOException {
        checkFile(src);
        createFileIfNotExists(dest);
        try (InputStream inStream = new BufferedInputStream(new FileInputStream(src));
             OutputStream outStream = new BufferedOutputStream(new FileOutputStream(dest))) {
            byte[] buffer = new byte[1024];
            int length;
            while (-1 != (length = inStream.read(buffer))) {
                outStream.write(buffer, 0, length);
            }
            outStream.flush();
        }
    }

    /**
     * 复制文件夹
     */
    public void copyDir(File src, File dest) throws IOException {
        if (!src.isDirectory()) {
            logger.error("{} must be a directory", src);
            throw new RuntimeException("file must be a directory");
        }
        recursionCopyDir(src, dest);
    }

    private void recursionCopyDir(File src, File dest) throws IOException {
        if (src.isFile()) {
            copyFile(src, dest);
        } else if (src.isDirectory()) {
            if (dest.getAbsolutePath().contains(src.getAbsolutePath())) {
                throw new RuntimeException("parent directory can't contains child directory");
            }
            if (!dest.mkdir()) {
                throw new RuntimeException("make directory error:" + dest);
            }
            File[] files = src.listFiles();
            if (files != null) {
                for (File file : files) {
                    recursionCopyDir(file, new File(dest, file.getName()));
                }
            }
        }
    }

    /**
     * 创建文件
     * 如果已存在则不重复创建
     */
    public void createFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            if (!file.createNewFile()) {
                logger.error("{} create fail", file);
                throw new RuntimeException("create file error" + file);
            }
        }
    }

    /**
     * 递归删除整个文件夹
     */
    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    if (!deleteDir(new File(dir, child))) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    public void reverse(File src, File dest) throws IOException {
        try (InputStream inStream = new FileInputStream(src);
             OutputStream outStream = new FileOutputStream(dest)) {
            int data;
            while ((data = inStream.read()) != -1) {
                outStream.write(data ^ 0xff);
            }
        }
    }
}
