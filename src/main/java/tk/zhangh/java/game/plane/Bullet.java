package tk.zhangh.java.game.plane;

import tk.zhangh.java.game.util.Constant;

import java.awt.*;

/**
 * 飞机大战，子弹类
 * Created by ZhangHao on 2016/4/27.
 */
public class Bullet extends GameObject{
    private double degree;

    public Bullet(){
        degree = Math.random() * Math.PI * 2;
        y  = Constant.WINDOW_HEIGHT/2;
        x = Constant.WINDOW_WIDTH/2;
        width = 10;
        height = 10;
        speed = 10;
    }

    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, width, height);
    }

    public void draw(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.yellow);
        g.fillOval((int) x, (int) y, width, height);
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        if (y > Constant.WINDOW_HEIGHT -height || y < height){
            degree = -degree;
        }
        if (x < 0 || x > Constant.WINDOW_WIDTH - width){
            degree = Math.PI-degree;
        }
        g.setColor(color);
    }
}
