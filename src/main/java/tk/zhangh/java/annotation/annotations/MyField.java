package tk.zhangh.java.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类属性注解，对应一个字段
 * Created by ZhangHao on 2016/6/8.
 */
@Target(ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
public @interface MyField {
    /**
     * 数据字段名称
     */
    String columnName();

    /**
     * 数据类型
     */
    String type();

    /**
     * 数据字段长度
     */
    int length();
}
