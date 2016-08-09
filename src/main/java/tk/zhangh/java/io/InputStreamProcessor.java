package tk.zhangh.java.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件输入处理接口
 * Created by ZhangHao on 2016/7/27.
 */
public interface InputStreamProcessor {
    void process(InputStream input) throws IOException;
}
