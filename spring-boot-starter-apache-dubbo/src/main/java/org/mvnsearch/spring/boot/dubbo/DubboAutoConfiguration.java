package org.mvnsearch.spring.boot.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * dubbo auto configuration
 *
 * @author linux_china
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DubboProperties properties;
    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig dubboApplicationConfig() {
        ApplicationConfig appConfig = new ApplicationConfig();
        if (properties.getApp() == null || properties.getApp().isEmpty()) {
            properties.setApp(env.getProperty("spring.application.name"));
        }
        appConfig.setName(properties.getApp());
        return appConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProtocolConfig dubboProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(properties.getProtocol());
        protocolConfig.setTransporter(properties.getTransporter());
        protocolConfig.setPort(properties.getPort());
        protocolConfig.setThreads(properties.getThreads());
        return protocolConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig dubboRegistryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(properties.getRegistry());
        registryConfig.setCheck(true);
        return registryConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.dubbo", name = "monitor")
    public MonitorConfig dubboMonitorConfig() {
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setAddress(properties.getMonitor());
        return monitorConfig;
    }

    @Bean
    public DubboOperationEndpoint dubboOperationEndpoint() {
        return new DubboOperationEndpoint();
    }

    @Bean
    public DubboHealthIndicator dubboHealthIndicator() {
        return new DubboHealthIndicator();
    }

    @Bean
    public DubboEndpoint dubboEndpoint() {
        return new DubboEndpoint();
    }

}
