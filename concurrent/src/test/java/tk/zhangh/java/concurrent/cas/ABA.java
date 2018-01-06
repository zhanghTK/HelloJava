package tk.zhangh.java.concurrent.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ABA问题
 * 并发场景下的ABA问题
 * 只希望充值成功一次，但出现反复充值
 * Created by ZhangHao on 2017/3/28.
 */
public class ABA {
    private static AtomicInteger account = new AtomicInteger(19);

    static class AddThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                while (true) {
                    Integer money = account.get();
                    if (money < 20) {
                        if (account.compareAndSet(money, money + 20)) {
                            // 充值成功
                            System.out.println("余额小于20元，充值成功，余额：" + (money + 20) + "元");
                            break;
                        } else {
                            // 充值失败，自旋
                            System.out.println("充值失败，自旋");
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // 三个线程模拟充值
        for (int i = 0; i < 3; i++) {
            new Thread(new AddThread(), "充值" + i).start();
        }

        // 一个线程模拟消费，尝试一百次
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    Integer money = account.get();
                    if (money > 10) {
//                        System.out.println("大于10元");
                        if (account.compareAndSet(money, money - 10)) {
                            System.out.println("成功消费10元，余额：" + (money - 10) + "元");
                            break;
                        } else {
                            System.out.println("消费失败，自旋");
                        }
                    } else {
                        System.out.println("没有足够的金额");
                        break;
                    }
                }
            }
        }, "消费").start();
    }
}
