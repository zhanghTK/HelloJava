package tk.zhangh.java.practice;

/**
 * 测试反射API中isAccessible方法中使用的model
 * 保证一个private字段，一个public字段
 * Created by ZhangHao on 2017/1/13.
 */
public class Employee {
    public String name;
    private String id;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
