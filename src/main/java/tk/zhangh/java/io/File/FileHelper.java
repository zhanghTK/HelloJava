package tk.zhangh.java.io.File;

import java.io.File;

/**
 * 文件帮助类
 * Created by ZhangHao on 2016/4/19.
 */
public class FileHelper {
    /**
     * 打印某路径下所有文件树结构
     * @param file
     * @param level
     */
    public static void printFile(java.io.File file, int level){
        for (int i = 0; i < level-1; i++) {
            System.out.print("\t");
        }
        System.out.println("├"+file.getName());
        if (file.isDirectory()){
            java.io.File[] files = file.listFiles();
            for (java.io.File fileTmp : files){
                printFile(fileTmp, level + 1);
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
}
