package tk.zhangh.java.copy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 * Created by ZhangHao on 2017/11/2.
 */
@Slf4j
public class CopyTest {

    private static final String PATH = "F:\\test\\iPhone_4.7_11.0.2_15A421_Restore.ipsw";

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 4; i++) {
            fileChannelCopy();
        }
        System.in.read();
    }

    private static void fileChannelCopy() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel read = new FileInputStream(PATH).getChannel();
        FileChannel writer = new RandomAccessFile(PATH + "-"
                + new Random(System.currentTimeMillis()).nextInt(), "rw").getChannel();
        long readSize = 0;
        long size = read.size() / 30;
        ByteBuffer toRead, toWrite = null;
        while (readSize < read.size() // 未读完
                && (read.size() - readSize) > size) {  // 剩余未读大于size
            toRead = read.map(FileChannel.MapMode.READ_ONLY, readSize, size);
            toWrite = writer.map(FileChannel.MapMode.READ_WRITE, readSize, size);
            toWrite.put(toRead);
            readSize += size;
            toRead.clear();
            toWrite.clear();
        }
        toRead = read.map(FileChannel.MapMode.READ_ONLY, readSize, read.size() - readSize);
        assert toWrite != null;
        toWrite.put(toRead);
        toRead.clear();
        toWrite.clear();
        read.close();
        writer.close();
        long end = System.currentTimeMillis();
//        log.info("FileChannel copy file using " + (end - start) / 1000 + "s");
        System.out.println("FileChannel copy file using " + (end - start) / 1000 + "s");
    }

    private static void fileChannelThreadCopy() {
        @AllArgsConstructor
        class CopyThread extends Thread {
            private long begin;
            private long end;
            private String src;
            private String dest;

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                try {
                    RandomAccessFile read = new RandomAccessFile(src, "r");
                    RandomAccessFile write = new RandomAccessFile(dest, "rw");
                    read.seek(begin);
                    write.seek(begin);
                    FileChannel readChannel = read.getChannel();
                    FileChannel writeChannel = write.getChannel();
                    FileLock lock = writeChannel.lock(begin, end - begin, false);
                    readChannel.transferTo(begin, end - begin, writeChannel);
                    lock.release();
                    read.close();
                    write.close();
                    log.info(getName() + " - FileChannel copy file using " + (System.currentTimeMillis() - start) / 1000 + "s");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String dest = PATH + "-" + new Random(System.currentTimeMillis()).nextInt();
        long length = new File(PATH).length();
        int times = 2;
        long size = length / times;
        for (int i = 0; i < times - 1; i++) {
            new CopyThread(size * i, size * (i + 1), PATH, dest).start();
        }
        new CopyThread(size * (times - 1), length, PATH, dest).start();
    }

    private static void fileReaderWriter() throws IOException {
        long start = System.currentTimeMillis();
        FileReader br = new FileReader(new File(PATH));
        FileWriter fr = new FileWriter(new File(PATH + "-" + new Random(System.currentTimeMillis()).nextInt()));
        char[] ch = new char[512];
        while (br.read(ch) != -1) {
            fr.write(ch);
        }
        long end = System.currentTimeMillis();
        br.close();
        fr.close();
        System.out.println("FileReader/Writer copy file using " + (end - start) / 1000 + "s");
    }

    private static void bufferedReaderWriter() throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new FileReader(new File(PATH)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PATH + "-" + new Random(System.currentTimeMillis()).nextInt())));
        char[] ch = new char[512];
        while (reader.read(ch) != -1) {
            writer.write(ch);
        }
        long end = System.currentTimeMillis();
        reader.close();
        writer.close();
        System.out.println("BufferedReader/Writer copy file using " + (end - start) / 1000 + "s");
    }

    private static void bufferedStreamCopy() throws IOException {
        long start = System.currentTimeMillis();
        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(new File(PATH)));
        BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(PATH + "-" + new Random(System.currentTimeMillis()).nextInt())));
        byte[] b = new byte[1024];
        while (inStream.read(b) != -1) {
            outStream.write(b);
            outStream.flush();
        }
        long end = System.currentTimeMillis();
        inStream.close();
        outStream.close();
        System.out.println("BufferedStream copy file using " + (end - start) / 1000 + "s");
    }

    private static void fileStreamCopy() throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream(new File(PATH));
        FileOutputStream fos = new FileOutputStream(PATH + "-" + new Random(System.currentTimeMillis()).nextInt());
        byte[] b = new byte[1024];
        while (fis.read(b) != -1) {
            fos.write(b);
            fos.flush();
        }
        long end = System.currentTimeMillis();
        fis.close();
        fos.close();
        System.out.println("FileInput/OutputStream copy file using " + (end - start) / 1000 + "s");
    }
}
