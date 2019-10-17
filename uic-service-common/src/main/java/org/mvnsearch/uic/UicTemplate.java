package org.mvnsearch.uic;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * uic template
 *
 * @author linux_china
 */
public interface UicTemplate {

    User findById(Long id);

    CompletableFuture<User> findByIdFuture(Long id);

    Optional<Long> isEmailUnique(String email);
}
