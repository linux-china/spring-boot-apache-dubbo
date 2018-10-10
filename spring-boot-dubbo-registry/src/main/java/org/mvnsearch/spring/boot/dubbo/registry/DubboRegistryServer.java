package org.mvnsearch.spring.boot.dubbo.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * dubbo registry server
 *
 * @author linux_china
 */
@SpringBootApplication
public class DubboRegistryServer {
    public static void main(String[] args) {
        SpringApplication.run(DubboRegistryServer.class, args);
    }
}
