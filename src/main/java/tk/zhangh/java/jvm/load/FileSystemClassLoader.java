package tk.zhangh.java.jvm.load;

import tk.zhangh.java.io.stream.InputStreamProcessingTemplate;
import tk.zhangh.java.io.stream.InputStreamProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义系统文件类加载器
 * Created by ZhangHao on 2016/8/12.
 */
public class FileSystemClassLoader extends ClassLoader{
    /**
     * 类所在包所在路径
     */
    private String rootDir;

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz;
        InputStreamProcessor processor = new InputStreamProcessor() {
            @Override
            public Object process(InputStream inStream) throws IOException {
                ByteArrayOutputStream oStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inStream.read(buffer)) != -1) {
                    oStream.write(buffer, 0, len);
                }
                return oStream.toByteArray();
            }
        };
        String path = rootDir + "/" + name.replace(".", "/") + ".class";
        byte[] classData = (byte[])InputStreamProcessingTemplate.process(path,processor);
        if (classData == null){
            throw new ClassNotFoundException();
        }else {
            clazz = defineClass(name, classData,0 , classData.length);
        }
        return clazz;
    }
}
