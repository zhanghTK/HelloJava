package tk.zhangh.java.game.solar;

import tk.zhangh.java.game.solar.util.GameUtil;

import java.awt.*;

/**
 * 附属星
 * Created by ZhangHao on 2016/4/22.
 */
public class Planet extends Star{
    double longAxis;
    double shortAxis;
    double speed;
    double degree;
    Star center;
    boolean satellite;

    public Planet(String imagePath, double longAxis, double shortAxis, double speed, Star center){
        super(GameUtil.getImage(imagePath));
        this.center = center;
        this.x = center.x + longAxis;
        this.y = center.y;
        this.longAxis = longAxis;
        this.shortAxis = shortAxis;
        this.speed = speed;
    }

    public Planet(String imagePath, double longAxis, double shortAxis, double speed, Star center, boolean satellite) {
        this(imagePath, longAxis, shortAxis, speed, center);
        this.satellite = satellite;
    }

    @Override
    public void draw(Graphics graphics){
        super.draw(graphics);
        move();
        if (!satellite){
            drawTrace(graphics);
        }
    }

    public void drawTrace(Graphics graphics){
        double xt,yt,wt,ht;
        wt = longAxis * 2;
        ht = shortAxis * 2;
        xt = (center.x + center.width/2)-longAxis;
        yt = (center.y + center.high /2) - shortAxis;
        Color color = graphics.getColor();
        graphics.setColor(Color.blue);
        graphics.drawOval((int) xt, (int)yt, (int)wt, (int)ht);
        graphics.setColor(color);
    }

    public void move(){
        x = (center.x + center.width/2) + longAxis * Math.cos(degree);
        y = (center.y + center.high /2) + shortAxis * Math.sin(degree);
        degree += speed;
    }
}
