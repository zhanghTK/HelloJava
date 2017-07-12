package tk.zhangh.java.concurrent.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ABA问题
 * 并发场景下的ABA问题
 * Created by ZhangHao on 2017/3/28.
 */
public class ABA {
    private static AtomicInteger account = new AtomicInteger(19);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    while (true) {
                        Integer money = account.get();
                        if (money < 20) {
                            if (account.compareAndSet(money, money + 20)) {
                                System.out.println("余额小于20元，充值成功，余额：" + account.get() + "元");
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }).start();
        }
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    Integer money = account.get();
                    if (money > 10) {
                        System.out.println("大于10元");
                        if (account.compareAndSet(money, money - 10)) {
                            System.out.println("成功消费10元，余额：" + account.get() + "元");
                            break;
                        }
                    } else {
                        System.out.println("没有足够的金额");
                        break;
                    }
                }
            }
        }).start();
    }
}
