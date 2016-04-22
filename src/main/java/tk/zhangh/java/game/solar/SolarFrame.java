package tk.zhangh.java.game.solar;

import tk.zhangh.java.game.solar.util.Constant;
import tk.zhangh.java.game.solar.util.GameUtil;
import tk.zhangh.java.game.solar.util.MyFrame;

import java.awt.*;

/**
 * 主窗口类
 * Created by ZhangHao on 2016/4/22.
 */
public class SolarFrame extends MyFrame {
    Image bg = GameUtil.getImage("images/solar/bg.jpg");
    Star sun = new Star("images/solar/sun.jpg", Constant.WINDOW_WIDTH/2, Constant.WINDOW_HEIGHT/2);
    Planet earth = new Planet("images/solar/Earth.jpg", 150, 100, 0.1, sun);
    Planet mars = new Planet("images/solar/Mars.jpg", 200, 130, 0.2, sun);
    Planet moons = new Planet("images/solar/moon.jpg", 30, 20, 0.3, earth, true);

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        sun.draw(g);
        earth.draw(g);
        mars.draw(g);
        moons.draw(g);
    }

    public static void main(String[] args) {
        new SolarFrame().lunchFrame();
    }
}
