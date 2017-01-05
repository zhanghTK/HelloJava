package tk.zhangh.java.entity.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体类映射Service
 * Created by ZhangHao on 2017/1/3.
 */
@Component
public class EntityDbService {
    private Logger logger = LoggerFactory.getLogger(EntityDbService.class);

    /**
     * 根据实体类生成建表语句
     *
     * @param classPath 实体类路径
     * @return 建表语句
     */
    public String getCreateTableSql(String classPath) {
        Class clazz;
        try {
            clazz = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            logger.error("class not fount {}", classPath);
            throw new IllegalArgumentException("class not found:" + classPath);
        }
        String tableName = getTableName(clazz);
        List<ColumnInfo> columnInfos = getColumnInfos(clazz);
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableName).append("(");
        for (ColumnInfo columnInfo : columnInfos) {
            String name = columnInfo.getName();
            String type = columnInfo.getType();
            String length = String.valueOf(columnInfo.getLength());
            sql.append(name).append(" ").append(type).append("(").append(length).append(")").append(", ");

        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(");");
        logger.info("according to {}, create table sql {}", classPath, sql.toString());
        return sql.toString();
    }

    /**
     * 获取实体类对应表名称
     *
     * @param clazz 实体类
     * @return 表名称
     */
    public String getTableName(Class clazz) {
        Table table = (Table) clazz.getAnnotation(Table.class);
        return table.value();
    }

    /**
     * 获取实体类对应表字段列表
     *
     * @param clazz 实体类
     * @return 表字段列表
     */
    public List<ColumnInfo> getColumnInfos(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnInfos.add(new ColumnInfo(column.name(), column.type(), column.length()));
            }
        }
        return columnInfos;
    }
}
