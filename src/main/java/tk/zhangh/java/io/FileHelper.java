package tk.zhangh.java.io;

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
    public void printFile(java.io.File file, int level){
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
}
