package tk.zhangh.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件输入处理模板
 * Created by ZhangHao on 2017/3/21.
 */
public class InStreamProcessingTemplate {
    public static Object process(File file, InputStreamProcessor processor) {
        IOException processException = null;
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            return processor.process(input);
        } catch (IOException e) {
            processException = e;
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    if (processException != null) {
                        throw new IOExceptionWrapping("Error message..." + file, processException);
                    } else {
                        throw new IOExceptionWrapping("Error closing InputStream for file " + file, e);
                    }
                }
            }
            if (processException != null) {
                throw new IOExceptionWrapping("Error processing InputStream for file " + file, processException);
            }
        }
    }

    public static Object process(String filePth, InputStreamProcessor processor) {
        return process(new File(filePth), processor);
    }
}
