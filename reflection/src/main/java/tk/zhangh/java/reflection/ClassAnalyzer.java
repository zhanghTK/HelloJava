package tk.zhangh.java.reflection;

import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 运行时类信息分析
 * Created by ZhangHao on 2017/1/5.
 */
@Component
public class ClassAnalyzer {
    /**
     * 获取类的信息
     *
     * @param clazz 类对象
     * @return 类信息，包含字段，构造方法，普通方法信息
     */
    public String getClassInfo(Class clazz) {
        StringBuilder classInfo = new StringBuilder();
        String modifier = Modifier.toString(clazz.getModifiers());
        String className = clazz.getName();
        String fieldInfo = getFieldsInfo(clazz);
        String constructorInfo = getConstructorsInfo(clazz);
        String methodInfo = getMethodsInfo(clazz);
        classInfo.append(modifier).append(" class ").append(className).append("{\n").
                append(fieldInfo).append("\n").
                append(constructorInfo).append("\n").
                append(methodInfo).append("}");
        return classInfo.toString();
    }

    /**
     * 类的构造器信息
     *
     * @param clazz 类对象
     * @return 构造器信息
     */
    public String getConstructorsInfo(Class clazz) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        StringBuilder constructorInfo = new StringBuilder();
        for (Constructor constructor : constructors) {
            String modifier = Modifier.toString(constructor.getModifiers());
            String constructorName = clazz.getName();
            Class[] paramTypes = constructor.getParameterTypes();
            StringBuilder param = new StringBuilder();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    param.append(", ");
                }
                param.append(paramTypes[i].getName());
            }
            constructorInfo.append("\t").append(modifier).append(" ").
                    append(constructorName).append("(").append(param).append(");\n");
        }
        return constructorInfo.toString();
    }

    /**
     * 类的方法信息
     *
     * @param clazz 类对象
     * @return 方法信息
     */
    public String getMethodsInfo(Class clazz) {
        Method[] methods = clazz.getMethods();
        StringBuilder methodInfo = new StringBuilder();
        for (Method method : methods) {
            String modifier = Modifier.toString(method.getModifiers());
            String retType = method.getReturnType().getName();
            String methodName = method.getName();
            Class[] paramTypes = method.getParameterTypes();
            StringBuilder param = new StringBuilder();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    param.append(", ");
                }
                param.append(paramTypes[i].getName());
            }
            methodInfo.append("\t").append(modifier).append(" ").
                    append(retType).append(" ").
                    append(methodName).append("(").append(param).append(");\n");
        }
        return methodInfo.toString();
    }

    /**
     * 类字段信息
     *
     * @param clazz 类对象
     * @return 字段信息
     */
    public String getFieldsInfo(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldInfo = new StringBuilder();
        for (Field field : fields) {
            String type = field.getType().getName();
            String modifier = Modifier.toString(field.getModifiers());
            String fieldName = field.getName();
            fieldInfo.append("\t").append(modifier).append(" ").append(type).append(" ").append(fieldName).append(";\n");
        }
        return fieldInfo.toString();
    }
}
