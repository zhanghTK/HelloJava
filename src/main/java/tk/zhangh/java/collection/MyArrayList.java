package tk.zhangh.java.collection;

/**
 * 模拟ArrayList
 * Created by ZhangHao on 2016/4/17.
 */
public class MyArrayList {
    private Object[] value;
    private int size;

    public MyArrayList() {
        this(10);
    }

    public MyArrayList(int size){
        if (size < 0){
            throw new RuntimeException("error size");
        }
        value = new Object[size];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public MyArrayList add(Object object){
        value[size] = object;
        size++;
        if (size >= value.length){
            int newCapacity = value.length * 2 + 2;
            Object[] newList = new Object[newCapacity];
            for (int i = 0; i < value.length; i++) {
                newList[i] = value[i];
            }
            value = newList;
        }
        return this;
    }

    public Object get(int index){
        rangeCheck(index);
        return value[index];
    }

    public boolean contains(Object object){
        return indexOf(object) >= 0;
    }

    public int indexOf(Object object){
        if (object == null){
            for (int i = 0; i < size; i++) {
                if (value[i] == null){
                    return i;
                }
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (object.equals(value[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Object object){
        if (object == null){
            for (int i = size - 1; i >=0; i--) {
                if (value[i] == null){
                    return i;
                }
            }
        }else {
            for (int i = size - 1; i >= 0; i--) {
                if (object.equals(value[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    public Object set(int index, Object object){
        rangeCheck(index);
        Object old = value[index];
        value[index] = object;
        return old;
    }

    public Object remove(int index) {
        rangeCheck(index);
        Object old = get(index);
        Object[] newList = new Object[value.length];
        for (int i = 0; i < index; i++) {
            newList[i] = value[i];
        }
        for (int i = index + 1; i < size; i++) {
            newList[i-1] = value[i];
        }
        value = newList;
        return old;
    }

    private void rangeCheck(int index){
        if (index < 0 || index >= size){
            throw new RuntimeException("error index");
        }
    }
}
