package tk.zhangh.java.reflection;

import org.junit.Test;

/**
 * Created by ZhangHao on 2016/3/20.
 * 测试反射获取类信息
 */
public class ClassAnalyzerTest {

    /**
     * 测试获取类构造器信息
     */
    @Test
    public void testGetConstructors(){
        System.out.println(ClassAnalyzer.getConstructors(Double.class));
    }

    /**
     * 测试获取类的方法信息
     */
    @Test
    public void testGetMethods() throws Exception {
        System.out.println(ClassAnalyzer.getMethods(Double.class));
    }

    /**
     * 测试获取类的字段信息
     */
    @Test
    public void testPrintField() throws Exception {
        System.out.println(ClassAnalyzer.getField(Double.class));
    }

    /**
     * 测试获取类的完整信息
     */
    @Test
    public void testPrintClassInformation() throws Exception {
        System.out.println(ClassAnalyzer.getClassInformation(Double.class));
    }
}