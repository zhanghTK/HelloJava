package tk.zhangh.java.game.plane;

import tk.zhangh.java.game.util.GameUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 飞机大战，飞机类
 * Created by ZhangHao on 2016/4/27.
 */
public class Plane extends GameObject{
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;

    private int width;
    private int height;

    public void draw(Graphics g){
        g.drawImage(image, (int)x, (int)y, null);
        move();
    }

    public Plane(String imagePath, double x, double y, int speed) {
        super();
        this.image = GameUtil.getImage(imagePath);
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public Plane(){}

    public void move(){
        if (left){
             x -= speed;
        }
        if (right){
            x += speed;
        }
        if (up){
            y -= speed;
        }
        if (down){
            y += speed;
        }
    }

    public void addDirection(KeyEvent e){
        switch (e.getKeyCode()){
            case 37:
                left = true;
                break;
            case 38:
                up = true;
                break;
            case 39:
                right = true;
                break;
            case 40:
                down = true;
                break;
            default:
                break;
        }
    }

    public void minusDirection(KeyEvent e){
        switch (e.getKeyCode()){
            case 37:
                left = false;
                break;
            case 38:
                up = false;
                break;
            case 39:
                right = false;
                break;
            case 40:
                down = false;
                break;
            default:
                break;
        }
    }

    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, width, height);
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

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
