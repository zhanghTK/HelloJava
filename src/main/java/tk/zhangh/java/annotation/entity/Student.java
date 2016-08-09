package tk.zhangh.java.annotation.entity;

import tk.zhangh.java.annotation.annotations.MyField;
import tk.zhangh.java.annotation.annotations.MyTable;

/**
 * student实体类
 * Created by ZhangHao on 2016/6/8.
 */
@MyTable("zh_student")
public class Student {
    @MyField(columnName = "id",type = "int", length = 10)
    private int id;

    @MyField(columnName = "name",type = "varchar", length = 10)
    private String name;

    @MyField(columnName = "age",type = "int", length = 3)
    private int age;

    private String ignore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (getId() != student.getId()) return false;
        if (getAge() != student.getAge()) return false;
        if (getName() != null ? !getName().equals(student.getName()) : student.getName() != null) return false;
        return !(getIgnore() != null ? !getIgnore().equals(student.getIgnore()) : student.getIgnore() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getAge();
        result = 31 * result + (getIgnore() != null ? getIgnore().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", ignore='" + ignore + '\'' +
                '}';
    }
}
