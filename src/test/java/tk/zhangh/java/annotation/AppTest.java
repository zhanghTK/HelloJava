package tk.zhangh.java.annotation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.zhangh.java.annotation.entity.Student;

import java.util.List;

/**
 * ���Ը���ʵ����ע������SQL���
 * Created by ZhangHao on 2016/8/8.
 */
public class AppTest {

    App app;

    /**
     * ��ʼ��
     */
    @Before
    public void InitApp() {
        app = new App();
    }

    /**
     * ���Լ�����
     * @throws Exception
     */
    @Test
    public void testGetClazz() throws Exception {
        Class clazz = app.getClazz("tk.zhangh.java.annotation.entity.Student");
        Assert.assertEquals(clazz, Student.class);
    }

    /**
     * ���Ի�ȡ����
     * @throws Exception
     */
    @Test
    public void testGetTableName() throws Exception {
        String tableName = app.getTableName(Student.class);
        Assert.assertEquals(tableName, "zh_student");
    }

    /**
     * ���Ի�ȡ����Ϣ
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
     * ���Դ���SQL
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