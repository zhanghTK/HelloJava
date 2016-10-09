package tk.zhangh.java.service;

/**
 * 服务提供者实现
 * Created by ZhangHao on 2016/10/9.
 */
public class EntityProvider implements Provider {
    static {
        ServiceManager.registerProvider("EntityService", new EntityProvider());
    }

    @Override
    public Service newService() {
        return new ServiceImpl();
    }

    /**
     * 服务实现
     */
    class ServiceImpl implements Service {
        @Override
        public void serve() {
            System.out.println("开始服务...");
        }
    }
}
