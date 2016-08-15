package tk.zhangh.java.jvm.load;

import tk.zhangh.java.io.stream.InputStreamProcessingTemplate;
import tk.zhangh.java.io.stream.InputStreamProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ZhangHao on 2016/8/13.
 */
public class ReverseClassLoader extends ClassLoader{
    private String rootDir;

    public ReverseClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz;
        InputStreamProcessor processor = new InputStreamProcessor() {
            @Override
            public Object process(InputStream inStream) throws IOException {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                int tmp;
                while ((tmp = inStream.read()) != -1) {
                    outStream.write(tmp ^ 0xff);
                }
                return outStream.toByteArray();
            }
        };
        String path = rootDir + "/" + name.replace(".", "/") + ".class";
        byte[] classData = (byte[]) InputStreamProcessingTemplate.process(path, processor);
        if (classData == null) {
            throw new ClassNotFoundException();
        }else {
            clazz =defineClass(name, classData, 0, classData.length);
        }
        return clazz;
    }
}
