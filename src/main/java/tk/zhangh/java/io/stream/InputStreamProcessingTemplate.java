package tk.zhangh.java.io.stream;

import tk.zhangh.java.exception.IOExceptionWrapping;

import java.io.*;

/**
 * 文件输入处理模板
 * Created by ZhangHao on 2016/8/9.
 */
public class InputStreamProcessingTemplate {
    public static Object process(File file, InputStreamProcessor processor){
        IOException processException = null;
        InputStream input = null;
        try{
            input = new FileInputStream(file);
            return processor.process(input);
        } catch (IOException e) {
            processException = e;
            return null;
        } finally {
            if(input != null){
                try {
                    input.close();
                } catch(IOException e){
                    if(processException != null){
                        throw new IOExceptionWrapping(processException, "Error message..." + file);
                    } else {
                        throw new IOExceptionWrapping(e, "Error closing InputStream for file " + file);
                    }
                }
            }
            if(processException != null){
                throw new IOExceptionWrapping(processException, "Error processing InputStream for file " + file);
            }
        }
    }

    public static Object process(String filePth, InputStreamProcessor processor){
        return process(new File(filePth), processor);
    }
}
