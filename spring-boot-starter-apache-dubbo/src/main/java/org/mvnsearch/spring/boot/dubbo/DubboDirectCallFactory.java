package org.mvnsearch.spring.boot.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Dubbo Direct call factory
 *
 * @author linux_china
 */
public class DubboDirectCallFactory {
    private static ApplicationConfig application = new ApplicationConfig("DubboConsumerTest");
    /**
     * dubbo service stubs
     */
    private static Map<String, Object> stubs = new HashMap<>();

    /**
     * 返回直连dubbo服务
     *
     * @param clazz dubbo service interface
     * @return remote service object
     */
    public static <T> T dubbo(String host, int port, Class<T> clazz) {
        return dubbo(host, port, clazz, "0.0.0");
    }

    /**
     * 返回直连dubbo服务
     *
     * @param clazz dubbo service interface
     * @return remote service object
     */
    @SuppressWarnings("unchecked")
    public static <T> T dubbo(String host, int port, Class<T> clazz, String version) {
        // 直连dubbo服务的URL
        String url = "dubbo://" + host + ":" + port + "/" + clazz.getCanonicalName() + "?version=" + version;
        if (!stubs.containsKey(url)) {
            ReferenceConfig<T> reference = new ReferenceConfig<T>();
            reference.setApplication(application);
            // 如果点对点直连，可以用reference.setUrl()指定目标地址，设置url后将绕过注册中心，
            // 其中，协议对应provider.setProtocol()的值，端口对应provider.setPort()的值，
            // 路径对应service.setPath()的值，如果未设置path，缺省path为接口名
            reference.setUrl(url);
            reference.setInterface(clazz);
            T stub = reference.get();
            stubs.put(url, stub);
        }
        return (T) stubs.get(url);
    }
}
