package tk.zhangh.java.game.plane;

import tk.zhangh.java.game.util.GameUtil;

import java.awt.*;

/**
 * Created by ZhangHao on 2016/4/28.
 */
public class Explode {
    private double x,y;
    private static Image[] images = new Image[16];
    int count;

    static {
        for (int i = 1; i <= 16; i++) {
            images[i-1] = GameUtil.getImage("images/plane/explode/e" + i + ".gif");
            images[i-1].getWidth(null);
        }
    }

    public void draw(Graphics g){
        if (count <= images.length) {
            g.drawImage(images[count++], (int) x, (int) y, null);
        }
    }

    public Explode(double x, double y){
        this.x = x;
        this.y = y;
    }
}
