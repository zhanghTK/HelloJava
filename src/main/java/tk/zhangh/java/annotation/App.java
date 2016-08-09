package tk.zhangh.java.annotation;

import tk.zhangh.java.annotation.annotations.MyField;
import tk.zhangh.java.annotation.annotations.MyTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据实体类生成建表语句
 * Created by ZhangHao on 2016/6/8.
 */
public class App {
    /**
     * 加载Class
     * @param classPath 全路径
     * @return Class
     */
    public Class getClazz(String classPath) {
        Class clazz = null;
        try {
            clazz = Class.forName(classPath);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * 获取表名
     * @param clazz 实体类
     * @return 表名
     */
    public  String getTableName(Class clazz) {
        MyTable myTable = (MyTable)clazz.getAnnotation(MyTable.class);
        return myTable.value();
    }

    /**
     * 获取属性信息
     * @param clazz 实体类
     * @return 属性信息集合
     */
    public List<Column> getColumns(Class clazz) {
        Field[] fields =  clazz.getDeclaredFields();
        List<Column> columns = new ArrayList<>();
        for (Field field : fields) {
            MyField myField = field.getAnnotation(MyField.class);
            if (myField != null) {
                columns.add(new Column(myField.columnName(), myField.type(), myField.length()));
            }
        }
        return columns;
    }

    /**
     * 创建SQL语句
     * @return SQL语句
     */
    public String createSql() {
        Class clazz = getClazz("tk.zhangh.java.annotation.entity.Student");
        String tableName = getTableName(clazz);
        List<Column> columns = getColumns(clazz);
        StringBuffer sql = new StringBuffer("CREATE TABLE ").append(tableName).append("(");
        for (int i = 0; i < columns.size(); i++) {
            String name = columns.get(i).getName();
            String type = columns.get(i).getType();
            String length = columns.get(i).getLength().toString();
            sql.append(name).append(" ").append(type).append("(").append(length).append(")");
            if (i != columns.size() - 1){
                sql.append(", ");
            }
        }
        sql.append(");");
        return sql.toString();
    }

    public static void main(String[] args){
        App app = new App();
        String sql = app.createSql();
        System.out.println(sql);
    }
}
