package tk.zhangh.java.annotation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.zhangh.java.annotation.entity.Student;

import java.util.List;

/**
 * 测试根据实体类注解生成SQL语句
 * Created by ZhangHao on 2016/8/8.
 */
public class AppTest {

    App app;

    /**
     * 初始化
     */
    @Before
    public void InitApp() {
        app = new App();
    }

    /**
     * 测试加载类
     * @throws Exception
     */
    @Test
    public void testGetClazz() throws Exception {
        Class clazz = app.getClazz("tk.zhangh.java.annotation.entity.Student");
        Assert.assertEquals(clazz, Student.class);
    }

    /**
     * 测试获取表名
     * @throws Exception
     */
    @Test
    public void testGetTableName() throws Exception {
        String tableName = app.getTableName(Student.class);
        Assert.assertEquals(tableName, "zh_student");
    }

    /**
     * 测试获取列信息
     * @throws Exception
     */
    @Test
    public void testGetColumns() throws Exception {
        List<Column> columns = app.getColumns(Student.class);
        Assert.assertEquals(columns.size(), 3);
        Assert.assertEquals(columns.get(0), new Column("id", "int", 10));
        Assert.assertEquals(columns.get(1), new Column("name", "varchar", 10));
        Assert.assertEquals(columns.get(2), new Column("age", "int", 3));
    }

    /**
     * 测试创建SQL
     * @throws Exception
     */
    @Test
    public void testCreateSql() throws Exception {
        String sql = "CREATE TABLE zh_student(id int(10), name varchar(10), age int(3));";
        Assert.assertEquals(app.createSql(), sql);

    }

    @Test
    public void testMain() throws Exception {

    }
}