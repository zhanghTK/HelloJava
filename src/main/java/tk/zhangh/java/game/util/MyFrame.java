package tk.zhangh.java.game.util;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 生成窗口基础类
 * Created by ZhangHao on 2016/4/22.
 */
public class MyFrame extends Frame {

    /**
     * 加载窗口
     */
    public void lunchFrame() {
        setSize(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
        setLocation(0, 0);
        setVisible(true);
        new PaintThread().start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * 重画窗口
     */
    class PaintThread extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
