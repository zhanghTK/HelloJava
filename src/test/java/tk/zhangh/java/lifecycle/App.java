package tk.zhangh.java.lifecycle;

/**
 * 测试生命周期运行
 * Created by ZhangHao on 2016/8/9.
 */
public class App {
    public static void main(String[] args) throws Exception{
        SocketServer socketServer = new SocketServer();
        socketServer.addLifecycleListener(new LifecycleListenerImp());
        socketServer.init();
        socketServer.start();
        socketServer.suspend();
        socketServer.resume();
        socketServer.destroy();
    }
}
