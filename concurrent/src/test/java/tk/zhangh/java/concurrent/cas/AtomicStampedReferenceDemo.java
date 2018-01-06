package tk.zhangh.java.concurrent.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference Demo
 * 使用AtomicStampedReference解决并发场景下的ABA问题
 * Created by ZhangHao on 2017/3/28.
 */
public class AtomicStampedReferenceDemo {
    private static AtomicStampedReference<Integer> account = new AtomicStampedReference<>(19, 0);

    public static void main(String[] args) {
        // 三个线程模拟充值
        for (int i = 0; i < 3; i++) {
            int timestamp = account.getStamp();
            new Thread(() -> {
                while (true) {  // 持续
                    while (true) {  // 自旋
                        Integer money = account.getReference();
                        if (money < 20) {
                            if (account.compareAndSet(money, money + 20, timestamp, timestamp + 1)) {
                                System.out.println("余额小于20元，充值成功，余额：" + (money + 20) + "员");
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }).start();
        }
        // 一个线程模拟消费，尝试一百次
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    int timestamp = account.getStamp();
                    Integer money = account.getReference();
                    if (money > 10) {
                        System.out.println("大于10元");
                        if (account.compareAndSet(money, money - 10, timestamp, timestamp + 1)) {
                            System.out.println("成功消费10元，余额：" + account.getReference() + "元");
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
