package tk.zhangh.java.concurrent.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * InterruptDemo
 * 通过轮询线程状态中断线程
 * 与中断相关的方法：
 * public void Thread.interrupt();  // 中断线程
 * public boolean Thread.isInterrupted();  // 判断是否被中断
 * public static boolean Thread.interrupted();  // 判断是否被中断，并清除当前中断状态
 * 注意：异常抛出后会清除中断标记位，如果catch要调用 Thread.currentThread.interrupt();恢复中断
 * Created by ZhangHao on 2017/3/27.
 */
public class InterruptDemo {
    private static void listFile(File file) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException("文件扫描任务被中断");
        }
        file = Optional.ofNullable(file).orElseThrow(() -> new IllegalArgumentException("扫描目录不能为空"));
        if (file.isFile()) {
            System.out.println(file);
            return;
        }
        File[] files = Optional.ofNullable(file.listFiles()).orElse(new File[]{});
        for (File f : files) {
            listFile(f);
        }
    }

    private static String readFromConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) throws Exception {
        Thread fileIteratorThread = new Thread(() -> {
            try {
                listFile(new File("c:\\"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consoleThread = new Thread(() -> {
            while (true) {
                if ("quit".equalsIgnoreCase(readFromConsole())) {
                    if (fileIteratorThread.isAlive()) {
                        fileIteratorThread.interrupt();
                        return;
                    }
                } else {
                    System.out.println("输入quit退出文件扫描");
                }
            }
        });
        consoleThread.start();
        fileIteratorThread.start();
    }
}
