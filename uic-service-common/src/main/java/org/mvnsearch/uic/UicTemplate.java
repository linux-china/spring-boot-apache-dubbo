package org.mvnsearch.uic;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * uic template
 *
 * @author linux_china
 */
public interface UicTemplate {

    User findById(Integer id);

    CompletableFuture<User> findByIdFuture(Integer id);

    Optional<Long> isEmailUnique(String email);
}
