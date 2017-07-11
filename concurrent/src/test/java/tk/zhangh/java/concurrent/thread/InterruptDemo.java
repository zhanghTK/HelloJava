package tk.zhangh.java.concurrent.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * InterruptDemo
 * 通过轮询线程状态中断线程
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
