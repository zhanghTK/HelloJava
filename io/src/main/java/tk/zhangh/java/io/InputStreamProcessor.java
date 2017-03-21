package tk.zhangh.java.io;

import java.io.InputStream;

/**
 * 文件输入处理接口
 * Created by ZhangHao on 2017/3/21.
 */
@FunctionalInterface
public interface InputStreamProcessor<T> {
    T process(InputStream inStream);
}
