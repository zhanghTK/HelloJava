package tk.zhangh.java.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Created by ZhangHao on 2016/3/24.
 * Connection包装类
 */
public class ConnectionWrap {
    private static String USERNAME;
    private static String PASSWORD;
    private static String CLASS_DRIVER;
    private static String URL;
    private boolean autoCommit;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ConnectionWrap() {
        autoCommit = true;
    }

    public ConnectionWrap(boolean autoCommit){
        this.autoCommit = autoCommit;
    }

    /**
     * 初始化操作
     * 加载数据库数据库驱动
     */
    static {
        InputStream inStream = null;
        try {
            inStream = ConnectionWrap.class.getResourceAsStream("/jdbc.properties");
            Properties properties = new Properties();
            properties.load(inStream);
            USERNAME = properties.getProperty("username");
            PASSWORD = properties.getProperty("password");
            CLASS_DRIVER = properties.getProperty("classDriver");
            URL = properties.getProperty("url");
            Class.forName(CLASS_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Connection getConnection() throws SQLException{
        if (connection == null){
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(autoCommit);
        }
        return connection;
    }

    /**
     * 初始化PreparedStatement
     * @param sql SQL语句
     * @param params 参数
     * @return 初始化后PreparedStatement引用
     * @throws SQLException
     */
    private PreparedStatement initPreparedStatement(String sql, List<Object> params)throws SQLException{
        preparedStatement = getConnection().prepareStatement(sql);
        if (params != null && !params.isEmpty()){
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i+1, params.get(i));
            }
        }
        return preparedStatement;
    }

    /**
     * 插入操作
     * @param sql SQL语句，使用？作为参数占位符
     * @param params 参数列表
     * @return 插入是否成功
     * @throws SQLException
     */
    public boolean insert(String sql, List<Object> params) throws SQLException{
        return update(sql, params);
    }

    /**
     * 删除操作
     * @param sql SQL语句，使用？作为占位符
     * @param params 参数列表
     * @return 删除是否成功
     * @throws SQLException
     */
    public boolean delete(String sql, List<Object> params) throws SQLException{
        return update(sql, params);
    }

    /**
     * 更新操作
     * @param sql SQL语句，使用？作为占位符
     * @param params 参数列表
     * @return 更新是否成功
     * @throws SQLException
     */
    public boolean update(String sql, List<Object> params){
        boolean flag = false;
        int result = -1;
        try {
            result = initPreparedStatement(sql, params).executeUpdate();
            if (autoCommit) {
                getConnection().commit();
            }
        } catch (SQLException e) {
            try {
                if (!autoCommit){
                    getConnection().rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        flag = result > 0 ? true : false;
        return flag;
    }

    /**
     * 查找操作，不指定返回结果对应model
     * @param sql SQL语句，使用？作为占位符
     * @param params 参数列表
     * @return 查询结果列表
     * @throws SQLException
     */
    public List<Map<String, Object>>find(String sql, List<Object> params){
        List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
        try {
            resultSet = initPreparedStatement(sql,params).executeQuery();
            if (!autoCommit){
                getConnection().commit();
            }
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()){
                Map<String, Object> row = new HashMap<String, Object>();
                for (int i = 0; i <columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(columnName);
                    columnValue = columnValue == null ? "" : columnValue;
                    row.put(columnName, columnValue);
                }
                table.add(row);
            }
        } catch (SQLException e) {
            try {
                if (!autoCommit) {
                    getConnection().rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 查找操作，指定返回结果对应model
     * @param sql SQL语句，使用？作为占位符
     * @param params 参数列表
     * @param tClass 查询结果对应类型
     * @param <T> 查询结果对应类型
     * @return 查询结果列表
     * @throws Exception
     */
    public <T> List<T> find(String sql, List<Object> params, Class<T> tClass){
        List<T> table = new ArrayList<T>();
        try {
            resultSet = initPreparedStatement(sql,params).executeQuery();
            if (!autoCommit){
                getConnection().commit();
            }
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()){
                T row = tClass.newInstance();
                for (int i = 0; i <columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(columnName);
                    columnValue = columnValue == null ? "" : columnValue;
                    Field field = tClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(row, columnValue);
                }
                table.add(row);
            }
        } catch (SQLException e) {
            try {
                if (!autoCommit) {
                    getConnection().rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 关闭数据库连接资源
     */
    public void releaseResources(){
        try {
            if (resultSet != null){
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                    preparedStatement = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null){
                        connection.close();
                        connection = null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
