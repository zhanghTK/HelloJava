package tk.zhangh.java.entity.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.zhangh.log.LogAopConf;

/**
 * 测试表结构与实体类映射Service
 * Created by ZhangHao on 2017/1/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AnnotationDbTestConfig.class, LogAopConf.class})
public class EntityDbServiceTest {
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE TK_STUDENT(id int(10), name varchar(10), age int(3));";
    private static final String ENTITY_PATH =
            "tk.zhangh.java.entity.db.Student";

    @Autowired
    private EntityDbService service;

    @Test
    public void test_get_create_sql() {
        Assert.assertEquals(CREATE_TABLE_SQL, service.getCreateTableSql(ENTITY_PATH));
    }

    @Test
    public void test_get_table_name() throws ClassNotFoundException {
        Class entityClazz = Class.forName(ENTITY_PATH);
        Assert.assertEquals("TK_STUDENT", service.getTableName(entityClazz));
    }

    @Test
    public void test_get_table_columnInfos() throws ClassNotFoundException {
        Class entityClazz = Class.forName(ENTITY_PATH);
        Assert.assertEquals(Student.class.getDeclaredFields().length - 1, service.getColumnInfos(entityClazz).size());
    }
}
