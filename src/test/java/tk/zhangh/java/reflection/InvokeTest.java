package tk.zhangh.java.reflection;

import org.junit.Test;
import tk.zhangh.java.reflection.model.User;

import java.lang.reflect.Method;

/**
 * Created by ZhangHao on 2016/3/21.
 * 测试反射调用方法
 */
public class InvokeTest {
    @Test
    public void testInvoke(){
        try {
            Class clazz = User.class;
            Object userObject = clazz.newInstance();
            Method getIdMethod = clazz.getMethod("getId", null);
            Method setIdMethod = clazz.getMethod("setId", int.class);

            Method getNameMethod = clazz.getMethod("getName", null);
            Method setNameMethod = clazz.getMethod("setName", String.class);

            System.out.println(setIdMethod.invoke(userObject, new Object[]{123}));
            System.out.println(setNameMethod.invoke(userObject, new Object[]{"Tom"}));
            System.out.println(getIdMethod.invoke(userObject, null));
            System.out.println(getNameMethod.invoke(userObject, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
