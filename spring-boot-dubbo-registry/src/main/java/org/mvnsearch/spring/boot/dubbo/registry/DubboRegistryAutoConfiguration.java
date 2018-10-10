package org.mvnsearch.spring.boot.dubbo.registry;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * dubbo registry auto configuration
 *
 * @author linux_china
 */
@Configuration
@ImportResource("classpath:/dubbo-registry-server.xml")
public class DubboRegistryAutoConfiguration {

}
