package tk.zhangh.toolkit;

import java.security.MessageDigest;

/**
 * 转码,编码工具类
 * Created by ZhangHao on 2017/3/18.
 */
public class CodingUtils {
    /**
     * 将byte数组转换成十六进制字符串表示形式
     *
     * @param bytes 待转化的byte数组
     * @return String 转化成的十六进制字符串
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hexStr = Integer.toHexString(0xff & b);
            String prefix = hexStr.length() == 1 ? "0" : "";
            hexString.append(prefix).append(hexStr);
        }
        return hexString.toString();
    }

    /**
     * 对目标字符串产生摘要
     *
     * @param data      目标字符串
     * @param encoding  字符串编码
     * @param algorithm 摘要算法
     * @return 对目标字符串产生的摘要
     */
    public static String digest(String data, String encoding, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);  // 摘要算法
            byte[] bytes = messageDigest.digest(data.getBytes(encoding));  // 字符编码
            return toHexString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("create digest error", e);
        }
    }

    /**
     * 使用MD5算法和UTF-8编码对目标字符串产生摘要
     *
     * @param data 目标字符串
     * @return 对目标字符串产生的摘要
     */
    public static String digest(String data) {
        return digest(data, "UTF-8", "md5");
    }
}
