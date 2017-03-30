package tk.zhangh.java.concurrent.pattern.single;

import lombok.Setter;

/**
 * 不可变类
 * Created by ZhangHao on 2017/3/30.
 */
@Setter
public final class Product {
    private final String no;
    private final String name;
    private final double price;

    public Product(String no, String name, double price) {
        this.no = no;
        this.name = name;
        this.price = price;
    }
}
