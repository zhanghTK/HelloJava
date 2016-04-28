package tk.zhangh.java.game.plane;

import tk.zhangh.java.game.util.GameUtil;

import java.awt.*;

/**
 * Created by ZhangHao on 2016/4/28.
 */
public class GameObject {
    Image image;
    double x;
    double y;
    int speed = 3;
    int width;
    int height;

    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, width, height);
    }

    public GameObject(){}

    public GameObject(String imagePath, double x, double y, int speed, int width, int height) {
        this.image = GameUtil.getImage(imagePath);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
