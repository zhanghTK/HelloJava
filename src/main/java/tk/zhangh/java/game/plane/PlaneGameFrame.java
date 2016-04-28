package tk.zhangh.java.game.plane;

import tk.zhangh.java.game.util.GameUtil;
import tk.zhangh.java.game.util.MyFrame;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * 飞机大战游戏窗口
 * Created by ZhangHao on 2016/4/27.
 */
public class PlaneGameFrame extends MyFrame{
    private Image bg = GameUtil.getImage("images/plane/bg.jpg");
    private Plane plane = new Plane("images/plane/plane.png", 50, 50, 15);
    private ArrayList<Bullet> bulletArrayList = new ArrayList<Bullet>();
    private Date startTime;
    private Date endTime;
    private Explode explode;

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        plane.draw(g);
        for (int i = 0; i < bulletArrayList.size(); i++) {
            Bullet bullet = bulletArrayList.get(i);
            bullet.draw(g);
            if (bullet.getRect().intersects(plane.getRect())){
                if (explode == null){
                    explode = new Explode(plane.getX(), plane.getY());
                }
                explode.draw(g);
                plane.setLive(false);
                if (endTime == null){
                    endTime = new Date();
                }
            }
            if (!plane.isLive()){
                int time = (int)(endTime.getTime()-startTime.getTime())/1000;
                printMessage(g, "time:" + time + "s", 20, 120, 260, Color.white);
                switch (time/10){
                    case 0:
                    case 1:
                        printMessage(g, "初级", 100, 100, 200, Color.white);
                        break;
                    case 2:
                    case 3:
                        printMessage(g, "中级", 100, 100, 200, Color.white);
                        break;
                    case 4:
                    case 5:
                        printMessage(g, "高级", 100, 100, 200, Color.white);
                        break;
                    default:
                        printMessage(g, "特级", 100, 100, 200, Color.white);
                        break;
                }
            }else {
                String time = String.valueOf((new Date().getTime()-startTime.getTime())/1000);
                printMessage(g, time, 10, 30, 50, Color.yellow);
            }
        }
    }

    public void printMessage(Graphics g, String str, int size,int x, int y, Color color){
        Color c = g.getColor();
        g.setColor(color);
        Font font = new Font("宋体", Font.BOLD, size);
        g.setFont(font);
        g.drawString(str, x, y);
        g.setColor(c);
    }

    @Override
    public void lunchFrame() {
        super.lunchFrame();
        addKeyListener(new KeyMonitor());

        for (int i = 0; i < 5; i++) {
            Bullet bullet = new Bullet();
            bullet.setSpeed(10);
            bulletArrayList.add(bullet);
        }
        startTime = new Date();
    }

    public static void main(String[] args) {
        new PlaneGameFrame().lunchFrame();
    }

    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("释放：" + e.getKeyCode());
            plane.minusDirection(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("按下：" + e.getKeyCode());
            plane.addDirection(e);
        }
    }
}
