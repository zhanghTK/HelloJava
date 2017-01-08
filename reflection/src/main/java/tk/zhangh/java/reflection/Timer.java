package tk.zhangh.java.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标记需要统计时长的方法
 * 只能对无参数方法使用
 * Created by ZhangHao on 2017/1/8.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {
}
