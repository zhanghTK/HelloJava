package tk.zhangh.java.collection;

import tk.zhangh.java.collection.model.Employee;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangHao on 2016/4/8.
 */
public class HashMapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<String, Employee>();
        staff.put("123", new Employee("Amy"));
        staff.put("234", new Employee("Harry"));
        staff.put("345", new Employee("Gray"));
        staff.put("456", new Employee("Francesca"));

        System.out.println(staff);

        staff.remove("234");

        staff.put("456", new Employee("Miller"));

        System.out.println(staff.get("123"));

        for (Map.Entry<String, Employee> entry : staff.entrySet()){
            String key = entry.getKey();
            Employee value = entry.getValue();
            System.out.println(key +":" + value);
        }
    }
}
