package org.mvnsearch.uic;

/**
 * user service
 *
 * @author linux_china
 */
public interface UserService {

    User findById(Integer id);

    void create(User user);
}
