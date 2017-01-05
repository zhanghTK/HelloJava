package tk.zhangh.java.entity.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类属性使用注解，对应数据库一个字段
 * Created by ZhangHao on 2017/1/3.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 字段名称
     */
    String name();

    /**
     * 字段类型
     */
    String type();

    /**
     * 字段长度
     */
    int length();
}
