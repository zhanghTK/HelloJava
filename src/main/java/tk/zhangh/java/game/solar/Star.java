package tk.zhangh.java.game.solar;

import tk.zhangh.java.game.solar.util.GameUtil;

import java.awt.*;

/**
 * 主星
 * Created by ZhangHao on 2016/4/22.
 */
public class Star {
    Image image;
    double x;
    double y;
    int width;
    int high;

    public void draw(Graphics graphics){
        graphics.drawImage(image,(int)x, (int)y, null);
    }

    public Star(){}

    public Star(Image image){
        this.image = image;
        this.width = image.getWidth(null);
        this.high = image.getHeight(null);
    }

    public Star(Image image, double x, double y) {
        this(image);
        this.x = x;
        this.y = y;
    }

    public Star(String imagePath, double x, double y) {
        this(GameUtil.getImage(imagePath), x, y);
        this.width = image.getWidth(null);
        this.high = image.getHeight(null);
    }
}
