package org.mvnsearch;

import org.junit.jupiter.api.Test;
import org.mvnsearch.spring.boot.dubbo.DubboDirectCallFactory;
import org.mvnsearch.uic.UicTemplate;
import org.mvnsearch.uic.User;

/**
 * dubbo direct call test
 *
 * @author linux_china
 */
public class DubboDirectCallTest {

    @Test
    public void sayHelloTest() {
        UicTemplate uicTemplate = DubboDirectCallFactory.dubbo("127.0.0.1", 28888, UicTemplate.class);
        User user = uicTemplate.findById(1);
        System.out.println(user.getNick());
    }

}
