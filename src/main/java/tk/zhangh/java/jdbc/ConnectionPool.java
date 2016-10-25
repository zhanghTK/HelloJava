package tk.zhangh.java.jdbc;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 简单的数据连接池实现
 * Created by ZhangHao on 2016/9/8.
 */
public class ConnectionPool implements DataSource {

    /**
     * 默认数据连接池大小
     */
    private static final int DEFAULT_SIZE = 3;

    /**
     * 数据库连接包装对象集合
     */
    private LinkedList<ConnectionWrap> pool = new LinkedList<>();

    /**
     * 默认构造方法
     */
    public ConnectionPool() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            pool.add(new ConnectionWrap());
        }
    }

    /**
     * 获取数据库连接包装对象
     * @return
     */
    public ConnectionWrap getConnectionWrap() {
        ConnectionWrap result;
        synchronized (pool) {
            if (pool.size() < 1) {
                try {
                    pool.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = getConnectionWrap();
            }else {
                result = pool.removeFirst();
            }
        }
        return result;
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        return getConnectionWrap().getConnection();
    }

    /**
     * 获取指定用户名、密码的数据库连接对象
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection;
        Properties properties = new Properties();
        try {
            InputStream inStream = new FileInputStream(new File("jdbc.properties"));
            properties.load(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = properties.getProperty("url");
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new RuntimeException("Unsupport Operation.");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new RuntimeException("Unsupport Operation.");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new RuntimeException("Unsupport operation.");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new RuntimeException("Unsupport operation.");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new RuntimeException("Unsupport Operation.");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return (T)this;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return DataSource.class.equals(iface);
    }
}
