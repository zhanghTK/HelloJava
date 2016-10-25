package tk.zhangh.java.common.timer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 统计方法运行时间
 *
 * 只能用于参数为空的方法
 *
 * Created by ZhangHao on 2016/10/25.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {
}
