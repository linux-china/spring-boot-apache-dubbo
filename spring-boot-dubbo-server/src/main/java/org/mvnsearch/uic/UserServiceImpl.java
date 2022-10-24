package org.mvnsearch.uic;

import net.datafaker.Faker;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * account manager implementation
 *
 * @author linux_china
 */
@Component
@DubboService(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    private Faker faker = new Faker();

    public User findById(Integer id) {
        System.out.println("service called");
        User user = new User();
        user.setId(id);
        user.setNick(faker.name().name());
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setEmail(faker.internet().emailAddress());
        return user;
    }

    public void create(User user) {
        //
    }
}
