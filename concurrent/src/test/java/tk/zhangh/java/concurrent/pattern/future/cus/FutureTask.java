package tk.zhangh.java.concurrent.pattern.future.cus;

/**
 * 客户端访问数据的接口
 * 内部委托RealData获取数据
 * Created by ZhangHao on 2017/3/30.
 */
public class FutureTask implements Task {
    protected RealTask realData;

    protected boolean finished = false;

    public synchronized void setRealData(RealTask realData) {
        if (!finished) {
            this.realData = realData;
            finished = true;
            notifyAll();
        }
    }


    @Override
    public synchronized String getResult() {
        while (!finished) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.getResult();
    }
}
