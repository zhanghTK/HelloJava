package tk.zhangh.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by ZhangHao on 2016/3/20.
 * 反射分析类的字段，方法，构造
 */
public class ClassAnalyzer {

    /**
     * 获取类的字段、方法、构造信息
     * @param clazz 类对象
     * @return 类信息
     */
    public static String getClassInformation(Class clazz){
        StringBuffer classInfo = new StringBuffer();
        String modifier = Modifier.toString(clazz.getModifiers());
        String className = clazz.getName();
        String fieldInfo = getField(clazz);
        String constructorInfo = getConstructors(clazz);
        String methodInfo = getMethods(clazz);
        classInfo.append(modifier + " class " + className + "{\n" + fieldInfo + "\n" + constructorInfo + "\n" + methodInfo + "}");
        return classInfo.toString();
    }

    /**
     * 类的构造器信息
     * @param clazz 类对象
     * @return 构造器信息
     */
    public static String getConstructors(Class clazz){
        Constructor[] constructors = clazz.getDeclaredConstructors();
        StringBuffer constructorInfo = new StringBuffer();
        for (Constructor constructor : constructors){
            String modifier = Modifier.toString(constructor.getModifiers());
            String constructorName = clazz.getName();
            Class[] paramTypes = constructor.getParameterTypes();
            StringBuffer param = new StringBuffer();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0){
                    param.append(", ");
                }
                param.append(paramTypes[i].getName());
            }
            constructorInfo.append("\t" + modifier + " " + constructorName + "(" + param + ");\n");
        }
        return constructorInfo.toString();
    }

    /**
     * 类的方法信息
     * @param clazz 类对象
     * @return 方法信息
     */
    public static String getMethods(Class clazz){
        Method[] methods = clazz.getMethods();
        StringBuffer methodInfo = new StringBuffer();
        for (Method method : methods){
            String modifier = Modifier.toString(method.getModifiers());
            String retType = method.getReturnType().getName();
            String methodName = method.getName();
            Class[] paramTypes = method.getParameterTypes();
            StringBuffer param = new StringBuffer();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0){
                    param.append(", ");
                }
                param.append(paramTypes[i].getName());
            }
            methodInfo.append("\t" + modifier + " " + retType + " " + methodName + "(" + param + ");\n");
        }
        return  methodInfo.toString();
    }

    /**
     * 类字段信息
     * @param clazz 类对象
     * @return 字段信息
     */
    public static String getField(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer fieldInfo = new StringBuffer();
        for (Field field : fields){
            String type = field.getType().getName();
            String modifier = Modifier.toString(field.getModifiers());
            String fieldName = field.getName();
            fieldInfo.append("\t" + modifier + " " + type + " " + fieldName + ";\n");
        }
        return fieldInfo.toString();
    }
}
