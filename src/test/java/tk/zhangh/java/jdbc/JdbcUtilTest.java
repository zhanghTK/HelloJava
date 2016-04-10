package tk.zhangh.java.jdbc;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.zhangh.java.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by ZhangHao on 2016/3/24.
 * jdbc helper 测试
 */
public class JdbcUtilTest {
    private JdbcHelper jdbcHelper;

    @Before
    public void initJdbcUtil(){
        jdbcHelper = new JdbcHelper(false);
    }

    @Test
    public void testUpdate() throws Exception {
        List params = new ArrayList(){{add("root"); add("1");}};
        boolean result = jdbcHelper.update("update user set name = ? where id = ?", params);
        jdbcHelper.releaseResources();
        Assert.assertEquals(result, true);
    }

    @Test
    public void testFind() throws Exception {
        List params = new ArrayList(){{add("1");}};
        List<Map<String, Object>> table = jdbcHelper.find("select * from user where id =?", params);
        jdbcHelper.releaseResources();
        for (int i = 0; i < table.size(); i++) {
            Map<String, Object> row = table.get(i);
            for (Map.Entry<String, Object> entry: row.entrySet()){
                System.out.print(entry.getValue() + "\t");
            }
            System.out.println();
        }
    }

    @Test
    public void testFindByType() throws Exception{
        List params = new ArrayList(){{add("1");}};
        List<User> table = jdbcHelper.find("select * from user where id =?", params, User.class);
        jdbcHelper.releaseResources();
        for (int i = 0; i < table.size(); i++) {
            User user = table.get(i);
            System.out.println(user.getId() + "\t" + user.getName());
        }
        System.out.println();
    }

    @Test
    public void testInsert() throws Exception {
        List params = new ArrayList(){{add(new Random().nextInt(1000)); add("user");}};
        boolean result = jdbcHelper.update("INSERT INTO `user` VALUES(? ,?)", params);
        jdbcHelper.releaseResources();
        Assert.assertEquals(result, true);
    }

    @Test
    public void testDelete() throws Exception {
        List params1 = new ArrayList(){{add("2"); add("1111");}};
        jdbcHelper.update("INSERT INTO `user` VALUES(? ,?)", params1);
        jdbcHelper.getConnection().commit();
        List params = new ArrayList(){{add("2");}};
        boolean result = jdbcHelper.update("DELETE FROM user WHERE id = ?", params);
        jdbcHelper.releaseResources();
        Assert.assertEquals(result, true);
    }
}