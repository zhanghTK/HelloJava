package tk.zhangh.java.collection.model;

/**
 * Created by ZhangHao on 2016/4/7.
 */
public class Item implements Comparable<Item>{
    private String description;
    private int partNumber;

    public Item(String description, int partNumber) {
        this.description = description;
        this.partNumber = partNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", partNumber=" + partNumber +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Item)) return false;

        Item item = (Item) object;

        if (partNumber != item.partNumber) return false;
        return getDescription().equals(item.getDescription());

    }

    @Override
    public int hashCode() {
        int result = getDescription().hashCode();
        result = 31 * result + partNumber;
        return result;
    }

    public int compareTo(Item other) {
        return Integer.compare(partNumber, other.partNumber);
    }
}
