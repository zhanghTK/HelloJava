package tk.zhangh.java.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by ZhangHao on 2016/3/21.
 * ��������������Ϣ
 */
public class ObjectAnalyzer {
    private ArrayList<Object> visited = new ArrayList<Object>();

    public String getObjectInfor(Object object){
        // �п�
        if (object == null){
            return "null";
        }
        // �ѷ���
        if (visited.contains(object)){
            return "...";
        }
        // ��ӷ���
        visited.add(object);
        // ��ȡ����
        Class clazz = object.getClass();
        // �����ַ�������
        if (clazz == String.class){
            return (String)object;
        }
        // ������������
        if (clazz.isArray()){
            String result = clazz.getComponentType() + "[]{";
            // ��������
            for (int i = 0; i < Array.getLength(object); i++) {
                if (i > 0){
                    result += ",";
                }
                Object valIndex = Array.get(object, i);
                if (clazz.getComponentType().isPrimitive()){
                    // ����Ԫ���ǻ�������
                    result += valIndex;
                }else {
                    // ����Ԫ�ز��ǻ������͡��ݹ����
                    result += getObjectInfor(valIndex);
                }
            }
            return result + "}";
        }

        // ������������
        String result = clazz.getName();
        do {
            result += "[";
            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields){
                // ������static�ֶΣ������ڶ�����Ϣ
                if (!Modifier.isStatic(field.getModifiers())){
                    if (!result.endsWith("[")){
                        result += ",";
                    }
                    result += field.getName() + " = ";  // ������
                    try {
                        Class fieldClass = field.getType();  // ��������
                        Object fieldVal =  field.get(object);  // ����ֵ
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
