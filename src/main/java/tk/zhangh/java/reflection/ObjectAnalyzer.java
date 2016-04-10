package tk.zhangh.java.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by ZhangHao on 2016/3/21.
 * 反射分析对象的信息
 */
public class ObjectAnalyzer {
    private ArrayList<Object> visited = new ArrayList<Object>();

    public String getObjectInfor(Object object){
        // 判空
        if (object == null){
            return "null";
        }
        // 已访问
        if (visited.contains(object)){
            return "...";
        }
        // 添加访问
        visited.add(object);
        // 获取类型
        Class clazz = object.getClass();
        // 处理字符串类型
        if (clazz == String.class){
            return (String)object;
        }
        // 处理数组类型
        if (clazz.isArray()){
            String result = clazz.getComponentType() + "[]{";
            // 遍历数组
            for (int i = 0; i < Array.getLength(object); i++) {
                if (i > 0){
                    result += ",";
                }
                Object valIndex = Array.get(object, i);
                if (clazz.getComponentType().isPrimitive()){
                    // 数组元素是基本类型
                    result += valIndex;
                }else {
                    // 数组元素不是基本类型。递归调用
                    result += getObjectInfor(valIndex);
                }
            }
            return result + "}";
        }

        // 处理引用类型
        String result = clazz.getName();
        do {
            result += "[";
            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields){
                // 不处理static字段，不属于对象信息
                if (!Modifier.isStatic(field.getModifiers())){
                    if (!result.endsWith("[")){
                        result += ",";
                    }
                    result += field.getName() + " = ";  // 属性名
                    try {
                        Class fieldClass = field.getType();  // 属性类型
                        Object fieldVal =  field.get(object);  // 属性值
                        if (fieldClass.isPrimitive()){
                            result += fieldVal;
                        }else {
                            result += getObjectInfor(fieldVal);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            result += "]";
            clazz = clazz.getSuperclass();
        }while (clazz != null);
        return result;
    }
}
