package tk.zhangh.java.datastructure;

import tk.zhangh.java.model.Employee;
import tk.zhangh.java.model.Item;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created by ZhangHao on 2016/4/8.
 */
public class TreeMapTest {
    public static void main(String[] args) {
        TreeMap<String, Employee> treeMap1 = new TreeMap<String, Employee>();
        treeMap1.put("123", new Employee("Amy"));
        treeMap1.put("234", new Employee("Harry"));
        treeMap1.put("345", new Employee("Gray"));
        treeMap1.put("456", new Employee("Francesca"));
        System.out.println(treeMap1);

        TreeMap<Item, String> treeMap2 = new TreeMap<Item, String>(new Comparator<Item>() {
            public int compare(Item o1, Item o2) {
                return o1.compareTo(o2);
            }
        });
        treeMap2.put(new Item("itemA",345), "A");
        treeMap2.put(new Item("itemB",234), "B");
        treeMap2.put(new Item("itemC",123), "C");

        System.out.println(treeMap2);
    }
}
