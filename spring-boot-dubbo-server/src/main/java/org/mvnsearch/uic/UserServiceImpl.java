package org.mvnsearch.uic;

import com.github.javafaker.Faker;
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
