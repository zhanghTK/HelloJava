package tk.zhangh.java.concurrent.pattern.future.cus;

/**
 * 数据处理线程
 * 封装FutureData和RealData调用
 * Created by ZhangHao on 2017/3/30.
 */
public class ClientThread extends Thread {
    FutureTask futureData;
    String query;

    public Task request(String query) {
        this.query = query;
        futureData = new FutureTask();
        start();
        return futureData;
    }

    @Override
    public void run() {
        RealTask realData = new RealTask();
        realData.process(query);
        futureData.setRealData(realData);
    }
}
