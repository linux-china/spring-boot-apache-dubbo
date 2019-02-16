package org.mvnsearch.spring.boot.dubbo;

import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.spring.ServiceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * dubbo provider auto configuration
 *
 * @author linux_china
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboProviderAutoConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private ProtocolConfig protocolConfig;
    @Autowired
    private RegistryConfig registryConfig;
    @Autowired
    private DubboProperties dubboProperties;
    @Autowired
    private ManagementServerProperties managementServerProperties;
    @Autowired
    private ServerProperties serverProperties;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() throws Exception {
        if (dubboProperties.getHttpCheckUrl() != null) {
            System.setProperty("DUBBO_HTTP_CHECK_URL", dubboProperties.getHttpCheckUrl());
        } else {
            String schema = managementServerProperties.getSsl() == null ? "http://" : "https://";
            Integer managementPort = managementServerProperties.getPort() == null ? serverProperties.getPort() : managementServerProperties.getPort();
            String managementHost;
            if (managementServerProperties.getAddress() != null) {
                managementHost = managementServerProperties.getAddress().getHostAddress();
            } else if (serverProperties.getAddress() != null) {
                managementHost = serverProperties.getAddress().getHostAddress();
            } else {
                managementHost = NetUtils.getLocalHost();
            }
            String dubboHTTPCheckURL = schema + managementHost + ":" + managementPort + managementServerProperties.getServlet().getContextPath() + "/health";
            System.setProperty("DUBBO_HTTP_CHECK_URL", dubboHTTPCheckURL);
        }
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DubboService.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            publishDubboService(entry.getKey(), entry.getValue());
        }
    }

    public void publishDubboService(String beanName, Object bean) throws Exception {
        DubboService service = applicationContext.findAnnotationOnBean(beanName, DubboService.class);
        ServiceBean<Object> serviceConfig = new ServiceBean<>();
        if (void.class.equals(service.interfaceClass())
                && "".equals(service.interfaceName())) {
            if (bean.getClass().getInterfaces().length > 0) {
                serviceConfig.setInterface(bean.getClass().getInterfaces()[0]);
            } else {
                throw new IllegalStateException("Failed to export remote service class " + bean.getClass().getName() + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
            }
        }
        serviceConfig.setApplicationContext(applicationContext);
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setApplicationEventPublisher(applicationContext);
        serviceConfig.setInterface(service.interfaceClass());
        serviceConfig.setVersion(service.version());
        serviceConfig.afterPropertiesSet();
        serviceConfig.setRef(bean);
        serviceConfig.export();
    }

}
