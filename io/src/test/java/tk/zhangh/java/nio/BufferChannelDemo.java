package tk.zhangh.java.nio;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer使用Demo
 * Created by ZhangHao on 2017/3/30.
 */
public class BufferChannelDemo {

    /**
     * buffer基本使用
     */
    public static void bufferDemo() throws IOException {
        // 获得channel
        FileInputStream inStream = new FileInputStream(new File(""));
        FileChannel fileChannel = inStream.getChannel();
        // 建立buffer和channel关系，使用
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fileChannel.read(byteBuffer);
        // 关闭
        fileChannel.close();
        byteBuffer.flip();
    }

    /**
     * 使用nio复制文件
     */
    public static void nioCopyFile(String resource, String destination) throws IOException {
        FileInputStream inStream = new FileInputStream(resource);
        FileOutputStream outStream = new FileOutputStream(destination);

        FileChannel readChannel = inStream.getChannel();
        FileChannel writeChannel = outStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (readChannel.read(buffer) != -1) {
            buffer.flip();  // 读写转换
            writeChannel.write(buffer);
            buffer.clear();
        }
        readChannel.close();
        writeChannel.close();
    }

    /**
     * buffer操作
     */
    public static void testBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(15);
        System.out.println("=====allocate buffer 15=====");
        printBufferInfo(buffer);
        System.out.println("=====start put=====");
        for (int i = 0; i < 10; i++) {
            buffer.put(((byte) i));
            printBufferInfo(buffer);
        }
        System.out.println("=====end put=====");
        System.out.println("=====flip=====");
        buffer.flip();
        printBufferInfo(buffer);
        System.out.println("=====get start=====");
        for (int i = 0; i < 5; i++) {
            buffer.get();
            printBufferInfo(buffer);
        }
        System.out.println("=====get end=====");
        System.out.println("=====flip=====");
        buffer.flip();
        printBufferInfo(buffer);
    }

    /**
     * 内存映射
     */
    public static void testBufferMap() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(System.getProperty("user.dir") + File.separator + "README.md.bck", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, randomAccessFile.length());
        while (mappedByteBuffer.hasRemaining()) {
            System.out.println(((char) mappedByteBuffer.get()));
        }
        mappedByteBuffer.put(0, ((byte) 98));
        randomAccessFile.close();
    }

    private static void printBufferInfo(Buffer buffer) {
        System.out.printf("position=%2d\t\tlimit=%2d\t\tcapacity=%2d\n",
                buffer.position(), buffer.limit(), buffer.capacity());
    }

    private static void bufferSimpleUsage() throws IOException {
        RandomAccessFile file = new RandomAccessFile(System.getProperty("user.dir") + File.separator + "README.md.bck", "rw");
        FileChannel inChannel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        int bytesRead = inChannel.read(buffer);
        while (bytesRead != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
            bytesRead = inChannel.read(buffer);
        }
        file.close();
    }

    public static void main(String[] args) throws IOException {
//        String file = System.getProperty("user.dir") + File.separator + "README.md";
//        nioCopyFile(file, file + ".bck");

        testBuffer();

//        testBufferMap();

//        bufferSimpleUsage();
    }
}
