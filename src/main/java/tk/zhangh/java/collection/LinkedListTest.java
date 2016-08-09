package tk.zhangh.java.collection;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ZhangHao on 2016/4/7.
 */
public class LinkedListTest {
    public static void main(String[] args) {
        List<String> list1 = new LinkedList<String>();
        list1.add("Amy");
        list1.add("Carl");
        list1.add("Erica");

        List<String> list2 = new LinkedList<String>();
        list2.add("Bob");
        list2.add("Doug");
        list2.add("Frances");
        list2.add("Gloria");

        ListIterator<String> iterator1 = list1.listIterator();
        ListIterator<String> iterator2 = list2.listIterator();

        while (iterator2.hasNext()){
            if (iterator1.hasNext()){
                iterator1.next();
            }
            iterator1.add(iterator2.next());
        }
        System.out.println(list1);

        iterator2 = list2.listIterator();
        while (iterator2.hasNext()){
            iterator2.next();
            if (iterator2.hasNext()){
                iterator2.next();
                iterator2.remove();
            }
        }
        System.out.println(list2);
    }
}
