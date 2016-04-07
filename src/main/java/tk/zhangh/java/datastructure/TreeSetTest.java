package tk.zhangh.java.datastructure;

import tk.zhangh.java.model.Item;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ZhangHao on 2016/4/7.
 */
public class TreeSetTest {
    public static void main(String[] args) {
        SortedSet<Item> parts = new TreeSet<Item>();
        parts.add(new Item("C", 123));
        parts.add(new Item("B", 234));
        parts.add(new Item("A", 345));
        System.out.println(parts);

        SortedSet<Item> sortedByDesc = new TreeSet<Item>(new Comparator<Item>() {
            public int compare(Item o1, Item o2) {
                String desc1 = o1.getDescription();
                String desc2 = o2.getDescription();
                return desc1.compareTo(desc2);
            }
        });
        sortedByDesc.addAll(parts);
        System.out.println(sortedByDesc);
    }
}
