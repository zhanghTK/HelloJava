package tk.zhangh.java.x.chat.server;

/**
 * 连接客户端socket的管道
 * Created by ZhangHao on 2016/6/6.
 */
public class ClientChannel implements Runnable {
    //
//    private ClientContent clientContent;
//
//    private User user;
//
//    private boolean isRunning = true;
//
//    public ClientChannel(Socket client, ClientContent clientContent) {
//        this.clientContent = clientContent;
//        user = new User(client);
//    }
//
//    /**
//     * 构造方法，初始化输入、输出流，所有客户端连接
//     *
//     * @param client  当前客户端socket
//     * @param clients 所有客户端socket管道
//     */
//    public ClientChannel(Socket client, List<ClientChannel> clients) {
//        try {
//            this.clients = clients;
//            this.dataInputStream = new DataInputStream(client.getInputStream());
//            this.dataOutputStream = new DataOutputStream(client.getOutputStream());
//            clients.add(this);
//            this.name = dataInputStream.readUTF();
//            sendMessage("欢迎" + name + "进入聊天室", true);
//            sendOther(this.name + "新加入聊天室", true);
//        } catch (IOException e) {
//            IoUtils.close(dataInputStream, dataOutputStream);
//            isRunning = false;
//            clients.remove(this);
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 接收客户端数据
//     *
//     * @return
//     */
//    private String receiveMsg() {
//        String msg = "";
//        try {
//            msg = dataInputStream.readUTF();
//        } catch (IOException e) {
//            IoUtils.close(dataInputStream);
//            isRunning = false;
//            clients.remove(this);
//            e.printStackTrace();
//        }
//        return msg;
//    }
//
//    /**
//     * 向客户端发送数据
//     *
//     * @param msg
//     */
//    public void sendMessage(String msg, boolean isSys) {
//        if (msg == null || msg.equals("")) {
//            return;
//        }
//        if (isSys) {
//            msg = "【系统消息】:" + msg;
//        }
//        try {
//            dataOutputStream.writeUTF(msg);
//            dataOutputStream.flush();
//        } catch (IOException e) {
//            IoUtils.close(dataOutputStream);
//            isRunning = false;
//            clients.remove(this);
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 向除本线程外所有客户端线程发送数据
//     */
//    public void sendOther(String msg, boolean isSys) {
//        if (msg.startsWith("@") && msg.contains(":")) {
//            // 私聊
//            String name = msg.substring(1, msg.indexOf(":"));
//            String content = msg.substring(msg.indexOf(":") + 1);
//            for (ClientChannel clientChannel : clients) {
//                if (clientChannel.name.equals(name)) {
//                    clientChannel.sendMessage(this.name + "私信" + name + "," + content, isSys);
//                }
//            }
//        } else {
//            // 群聊
//            for (ClientChannel clientChannel : clients) {
//                if (clientChannel == this) {
//                    continue;
//                }
//                clientChannel.sendMessage(msg, isSys);
//            }
//        }
//    }
//
//    private void sendSecretMessage(String to, String message) {
//        String context = "【私信】" + this.name + ":";
//        clients.stream()
//                .filter(clientChannel -> clientChannel.name.equals(to))
//                .findFirst().ifPresent(clientChannel -> clientChannel.sendMessage(context));
//    }
//
//    private void sendMessage(String message) {
//        clients.forEach(clientChannel -> sendMessage(message, false));
//    }
//
//    private void sendSysNotice(String message) {
//        clients.forEach(clientChannel -> sendMessage(message, true));
//    }
//
//    /**
//     * 启动线程
//     */
    @Override
    public void run() {
//        while (isRunning) {
//            sendOther(receiveMsg(), false);
//        }
    }
}
