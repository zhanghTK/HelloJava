package tk.zhangh.java.entity.db;

import lombok.Data;

/**
 * 表字段信息
 * Created by ZhangHao on 2017/1/3.
 */
@Data
public class ColumnInfo {
    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 字段长度
     */
    private Integer length;

    public ColumnInfo(String name, String type, Integer length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }
}
