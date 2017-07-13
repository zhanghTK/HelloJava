package tk.zhangh.java.nio.demo1;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO版本多线程服务器
 * Created by ZhangHao on 2017/3/31.
 */
public class MultiThreadNIOEchoServer {
    private static Map<Socket, Long> time_stat = new HashMap<>();
    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        MultiThreadNIOEchoServer echoServer = new MultiThreadNIOEchoServer();
        try {
            echoServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws Exception {
        // 获取selector
        selector = SelectorProvider.provider().openSelector();

        // 创建非阻塞的server socket，绑定8000端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8000));

        // channel在selector注册，当有ACCEPT事件发生需要selector时通知channel
        SelectionKey acceptKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            selector.select();  // 阻塞等待IO准备好
            Set readyKeys = selector.selectedKeys();  // selectionKey表示selector和channel的关系
            Iterator i = readyKeys.iterator();
            long e;
            while (i.hasNext()) {
                SelectionKey key = (SelectionKey) i.next();
                i.remove();  // 获取到后删除，否则重复处理

                if (key.isAcceptable()) {
                    // 检测key的channel是否准备好接受一个新的socket
                    doAccept(key);
                } else if (key.isValid() && key.isReadable()) {
                    // 检测key是否有效并且key的channel是否准备好读
                    if (!time_stat.containsKey(((SocketChannel) key.channel()).socket())) {
                        time_stat.put(((SocketChannel) key.channel()).socket(), System.currentTimeMillis());
                    }
                    doRead(key);
                } else if (key.isValid() && key.isWritable()) {
                    // 检测key是否有效并且key的channel是否准备好写
                    doWrite(key);
                    System.out.println("spend:"
                            + (System.currentTimeMillis() - time_stat.remove(((SocketChannel) key.channel()).socket()))
                            + "ms");
                }
            }
        }
    }

    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();
        ByteBuffer bb = outq.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }
            if (bb.remaining() == 0) {
                outq.removeLast();
            }
        } catch (Exception e) {
            disconnect(sk);
        }
        if (outq.size() == 0) {
            // 修改感兴趣事件，只对读感兴趣
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void doRead(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
        int len;
        try {
            len = channel.read(byteBuffer);
            if (len < 0) {
                disconnect(key);
                return;
            }
        } catch (Exception e) {
            disconnect(key);
            return;
        }
        byteBuffer.flip();
        // 使用线程池执行业务
        tp.execute(new HandleMsg(key, byteBuffer));
    }

    private void disconnect(SelectionKey sk) {
    }

    private void doAccept(SelectionKey sk) {
        // 从SelectionKey获取channel
        ServerSocketChannel serverChannel = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            // 从channel获得客户端channel，设置非阻塞
            clientChannel = serverChannel.accept();
            clientChannel.configureBlocking(false);
            // 客户端channel注册到selector，当READ事件发生时通知客户端channel
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            EchoClient echoClient = new EchoClient();
            // 将客户端数据保存到SelectionKey
            clientKey.attach(echoClient);

            System.out.println("Accepted connection from " + clientChannel.socket().getInetAddress().getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 封装一个客户端
    private class EchoClient {
        // 存放客户端数据
        @Getter
        private LinkedList<ByteBuffer> outputQueue = new LinkedList<>();

        private void enQueue(ByteBuffer bb) {
            outputQueue.addFirst(bb);
        }
    }

    // 业务处理线程
    @AllArgsConstructor
    private class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();  // 取出doAccept中保存的客户端数据
            echoClient.enQueue(bb);
            // 添加感兴趣事件WRITE
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            // 强迫selector立即返回
            selector.wakeup();
        }
    }
}
