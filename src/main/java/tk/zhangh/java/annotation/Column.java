package tk.zhangh.java.annotation;

/**
 * 数据库字段信息
 * Created by ZhangHao on 2016/8/8.
 */
public class Column {
    private String name;
    
    private String type;
    
    private Integer lenght;

    public Column() {
    }

    public Column(String name, String type, Integer lenght) {
        this.name = name;
        this.type = type;
        this.lenght = lenght;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;

        Column column = (Column) o;

        if (getName() != null ? !getName().equals(column.getName()) : column.getName() != null) return false;
        if (getType() != null ? !getType().equals(column.getType()) : column.getType() != null) return false;
        return !(getLength() != null ? !getLength().equals(column.getLength()) : column.getLength() != null);

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", lenght=" + lenght +
                '}';
    }
}
