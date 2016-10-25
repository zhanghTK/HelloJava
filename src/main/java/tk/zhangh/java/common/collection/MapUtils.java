package tk.zhangh.java.common.collection;

import java.util.*;

/**
 * Map数据结构的扩展方法
 *
 * Created by ZhangHao on 2016/10/25.
 */
public class MapUtils {

    /**
     * 根据value值大小对Map进行排序
     * @param map 待排序的Map
     * @param <K> 键
     * @param <V> 值，要求实现Comparable，可以排序
     * @return 已排序的LinkedHashMap
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, (Map.Entry<K, V> o1, Map.Entry<K, V> o2) -> o2.getValue().compareTo(o1.getValue()));
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
