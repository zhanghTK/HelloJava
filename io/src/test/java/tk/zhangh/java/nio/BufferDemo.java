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
public class BufferDemo {
    public static void nioCopyFile(String resource, String destination) throws IOException {
        FileInputStream inStream = new FileInputStream(resource);
        FileOutputStream outStream = new FileOutputStream(destination);

        FileChannel readChannel = inStream.getChannel();
        FileChannel writeChannel = outStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (readChannel.read(buffer) != -1) {
            buffer.flip();
            writeChannel.write(buffer);
            buffer.clear();
        }
        readChannel.close();
        writeChannel.close();
    }

    public static void testBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(15);
        printBufferInfo(buffer);
        for (int i = 0; i < 10; i++) {
            buffer.put(((byte) i));
        }
        printBufferInfo(buffer);
        buffer.flip();
        printBufferInfo(buffer);
        for (int i = 0; i < 5; i++) {
            System.out.println(buffer.get());
        }
        printBufferInfo(buffer);
        buffer.flip();
        printBufferInfo(buffer);
    }

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
        System.out.println("position=" + buffer.position() + " limit=" + buffer.limit() + " capacity=" + buffer.capacity());
    }

    public static void main(String[] args) throws IOException {
//        String file = System.getProperty("user.dir") + File.separator + "README.md";
//        FileHelper.nioCopyFile(file, file+ ".bck");

//        testBuffer();

        testBufferMap();
    }
}
