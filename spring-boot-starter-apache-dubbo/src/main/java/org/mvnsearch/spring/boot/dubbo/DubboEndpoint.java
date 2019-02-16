package org.mvnsearch.spring.boot.dubbo;

import io.micrometer.core.instrument.MeterRegistry;
import org.mvnsearch.spring.boot.dubbo.listener.ConsumerSubscribeListener;
import org.mvnsearch.spring.boot.dubbo.listener.ProviderExportListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * dubbo endpoint
 *
 * @author linux_china
 */
@Endpoint(id = "dubbo")
public class DubboEndpoint implements ApplicationContextAware {
    @Autowired
    private MeterRegistry metrics;
    @Autowired
    private DubboProperties dubboProperties;
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ReadOperation
    public Object invoke() {
        Map<String, Object> info = new HashMap<String, Object>();
        boolean serverMode = false;
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(EnableDubboConfiguration.class);
        if (beanNames != null && beanNames.length > 0) {
            serverMode = true;
        }
        if (serverMode) {
            info.put("server", true);
            info.put("port", dubboProperties.getPort());
        }
        info.put("app", dubboProperties.getApp());
        info.put("registry", dubboProperties.getRegistry());
        info.put("protocol", dubboProperties.getProtocol());
        //published services
        Map<String, Map<String, Double>> publishedInterfaceList = new HashMap<>();
        Set<Class> publishedInterfaces = ProviderExportListener.exportedInterfaces;
        for (Class clazz : publishedInterfaces) {
            String interfaceClassCanonicalName = clazz.getCanonicalName();
            if (!interfaceClassCanonicalName.equals("void")) {
                Map<String, Double> methodNames = new HashMap<>();
                for (Method method : clazz.getMethods()) {
                    String key = clazz.getCanonicalName() + "." + method.getName();
                    methodNames.put(method.getName(), metrics.counter(key).count());
                }
                publishedInterfaceList.put(interfaceClassCanonicalName, methodNames);
            }
        }
        if (!publishedInterfaceList.isEmpty()) {
            info.put("publishedInterfaces", publishedInterfaceList);
        }
        //subscribed services
        Set<Class> subscribedInterfaces = ConsumerSubscribeListener.subscribedInterfaces;
        if (!subscribedInterfaces.isEmpty()) {
            try {
                Map<String, Map<String, Double>> subscribedInterfaceList = new HashMap<>();
                for (Class clazz : subscribedInterfaces) {
                    Map<String, Double> methodNames = new HashMap<>();
                    for (Method method : clazz.getMethods()) {
                        String key = clazz.getCanonicalName() + "." + method.getName();
                        methodNames.put(method.getName(), metrics.counter(key).count());
                    }
                    subscribedInterfaceList.put(clazz.getCanonicalName(), methodNames);
                }
                info.put("subscribedInterfaces", subscribedInterfaceList);
            } catch (Exception ignore) {

            }
            info.put("connections", ConsumerSubscribeListener.connections);
        }
        return info;
    }
}
