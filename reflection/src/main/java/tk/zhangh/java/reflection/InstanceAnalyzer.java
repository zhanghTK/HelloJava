package tk.zhangh.java.reflection;

import org.springframework.stereotype.Component;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * 运行时对象信息分析
 * Created by ZhangHao on 2017/1/3.
 */
@Component
public class InstanceAnalyzer {
    public String getInstanceInfo(Object object) {
        return new ObjectInfoUtil().getObjInfo(object);
    }

    private class ObjectInfoUtil {
        private ArrayList<Object> visited = new ArrayList<>();

        private String getObjInfo(Object obj) {
            String objInfo;
            if (obj == null) {
                objInfo = "null";
            } else if (visited.contains(obj)) {
                objInfo = "...";
            } else {
                visited.add(obj);
                Class clazz = obj.getClass();
                if (clazz == String.class) {
                    objInfo = (String) obj;
                } else if (clazz.isArray()) {
                    objInfo = getArrayInfo(obj, clazz);
                } else {
                    objInfo = getInstanceInfo(obj, clazz);
                }
            }
            return objInfo;
        }

        private String getInstanceInfo(Object obj, Class clazz) {
            String result = clazz.getName();
            do {
                result += "[";
                Field[] fields = clazz.getDeclaredFields();
                AccessibleObject.setAccessible(fields, true);
                for (Field field : fields) {
                    // 不处理static字段，不属于对象信息
                    if (!Modifier.isStatic(field.getModifiers())) {
                        if (!result.endsWith("[")) {
                            result += ",";
                        }
                        result += field.getName() + " = ";
                        try {
                            Class fieldClass = field.getType();
                            Object fieldVal = field.get(obj);
                            if (fieldClass.isPrimitive()) {
                                result += fieldVal;
                            } else {
                                result += getObjInfo(fieldVal);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                result += "]";
                clazz = clazz.getSuperclass();
            } while (clazz != null);
            return result;
        }

        private String getArrayInfo(Object obj, Class clazz) {
            String result = clazz.getComponentType() + "[]{";

            for (int i = 0; i < Array.getLength(obj); i++) {
                if (i > 0) {
                    result += ",";
                }
                Object valIndex = Array.get(obj, i);
                if (clazz.getComponentType().isPrimitive()) {
                    result += valIndex;
                } else {
                    result += getObjInfo(valIndex);
                }
            }
            return result + "}";
        }
    }
}
