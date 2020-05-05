package org.mvnsearch.spring.boot.dubbo;

import org.apache.dubbo.config.DubboShutdownHook;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * dubbo operation endpoint
 *
 * @author linux_china
 */
@RestControllerEndpoint(id = "dubboOps")
public class DubboOperationEndpoint {
    public static Boolean OFFLINE = false;


    @RequestMapping("/offline")
    public String offline() {
        DubboShutdownHook.getDubboShutdownHook().doDestroy();
        OFFLINE = true;
        return "success";
    }
}
