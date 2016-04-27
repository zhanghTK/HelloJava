package tk.zhangh.java.game.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * 游戏帮助类
 * Created by ZhangHao on 2016/4/20.
 */
public class GameUtil {
    /**
     * 读取图片
     * @param path 文件路径
     * @return Image文件
     */
    public static Image getImage(String path){
        URL url = GameUtil.class.getClassLoader().getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
