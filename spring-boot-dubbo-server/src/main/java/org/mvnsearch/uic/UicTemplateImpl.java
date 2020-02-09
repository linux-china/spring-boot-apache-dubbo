package org.mvnsearch.uic;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * uic template implementation
 *
 * @author linux_china
 */
@Component
@DubboService(interfaceClass = UicTemplate.class)
public class UicTemplateImpl implements UicTemplate {
    public User findById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setNick("nick:" + id);
        return user;
    }

    @Override
    public CompletableFuture<User> findByIdFuture(Integer id) {
        return CompletableFuture.completedFuture(findById(id));
    }

    public Optional<Long> isEmailUnique(String email) {
        return Optional.of(1L);
    }
}
