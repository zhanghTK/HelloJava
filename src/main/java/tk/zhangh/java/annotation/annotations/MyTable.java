package tk.zhangh.java.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类注解，对应一张表
 * Created by ZhangHao on 2016/6/8.
 */
@Target(ElementType.TYPE )
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTable {
    String value();
}
