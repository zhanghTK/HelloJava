package tk.zhangh.java.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心，包含：
 * Provider Registration API
 * Service Access API
 * Created by ZhangHao on 2016/10/9.
 */
public class ServiceManager {
    private static final Map<String, Provider> providers = new ConcurrentHashMap<>();
    public static final String DEFAULT_PROVIDER_NAME = "def";

    private ServiceManager() {}

    public static void registerDefaultProvider(Provider provider) {
        registerProvider(DEFAULT_PROVIDER_NAME, provider);
    }

    // 提供者注册API
    public static void registerProvider(String name, Provider p) {
        providers.put(name, p);
    }

    // 服务访问API(默认)
    public static Service newInstance() {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }
    // 服务访问API
    public static Service newInstance(String name) {

        Provider p = providers.get(name);
        if(p == null) {
            throw new IllegalArgumentException("No provider registered with name: " + name);
        }
        return p.newService();
    }
}
