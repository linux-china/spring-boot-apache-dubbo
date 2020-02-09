package org.mvnsearch;

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
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User user(@PathVariable Integer id) {
        return userService.findById(id);
    }
}
