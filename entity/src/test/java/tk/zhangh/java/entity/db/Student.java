package tk.zhangh.java.entity.db;

import lombok.Data;

/**
 * 实体类
 * Created by ZhangHao on 2017/1/3.
 */
@Data
@Table("TK_STUDENT")
public class Student {
    @Column(name = "id", type = "int", length = 10)
    private int id;

    @Column(name = "name", type = "varchar", length = 10)
    private String name;

    @Column(name = "age", type = "int", length = 3)
    private int age;

    private String ignore;
}
