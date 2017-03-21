package tk.zhangh.java.practice;

import java.util.Random;
import java.util.function.Supplier;

/**
 * 对比String和StringBuilder
 * Created by ZhangHao on 2017/3/20.
 */
public class StringVsStringBuilder {

    private static final int times = 10000000;

    public static void main(String[] args) {
        System.out.println("String          :" + time(StringVsStringBuilder::stitchStr));
        System.out.println("String With Loop:" + time(StringVsStringBuilder::stitchStrWithLoop));
        System.out.println("StringBuilder   :" + time(StringVsStringBuilder::stitchStrBuilder));
    }

    private static long time(Supplier<String> stringSupplier) {
        long t0 = System.nanoTime();
        for (int i = 0; i < times; i++) {
            stringSupplier.get();
        }
        long t1 = System.nanoTime();
        return (t1 - t0) / 1_000_000;
    }

    private static String stitchStr() {
        ThreadLocal threadLocal = new ThreadLocal();
        return getRandom() + getRandom() + getRandom() + getRandom() + getRandom()
                + getRandom() + getRandom() + getRandom() + getRandom() + getRandom();
    }

    private static String stitchStrWithLoop() {
        String result = "";
        for (int i = 0; i < 10; i++) {
            result += getRandom();
        }
        return result;
    }

    private static String stitchStrBuilder() {
        StringBuilder result = new StringBuilder();
        result.append(getRandom()).append(getRandom()).append(getRandom()).append(getRandom()).append(getRandom())
                .append(getRandom()).append(getRandom()).append(getRandom()).append(getRandom()).append(getRandom());
        return result.toString();
    }

    private static String getRandom() {
        Random random = new Random(System.currentTimeMillis());
        return String.valueOf(random.nextInt(Integer.MAX_VALUE));
    }
}
