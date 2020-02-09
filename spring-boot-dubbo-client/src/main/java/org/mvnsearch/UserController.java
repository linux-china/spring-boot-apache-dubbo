package org.mvnsearch;

import com.github.javafaker.Faker;
import org.mvnsearch.uic.User;
import org.mvnsearch.uic.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller
 *
 * @author linux_china
 */
@RestController
public class UserController {
    private Faker faker = new Faker();
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User user(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/static")
    public User staticShow() {
        User user = new User();
        user.setId(100);
        user.setNick(faker.name().name());
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setEmail(faker.internet().emailAddress());
        return user;
    }

}
