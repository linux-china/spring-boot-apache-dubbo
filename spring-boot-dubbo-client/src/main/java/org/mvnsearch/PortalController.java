package org.mvnsearch;

import org.mvnsearch.uic.AccountManager;
import org.mvnsearch.uic.UicTemplate;
import org.mvnsearch.uic.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

/**
 * portal controller
 *
 * @author linux_china
 */
@Controller
public class PortalController {
    @Autowired
    private UicTemplate uicTemplate;
    @Autowired
    private AccountManager accountManager;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("user", uicTemplate.findById(1L));
        return "index";
    }

    @RequestMapping("/reactive/user")
    @ResponseBody
    public Mono<User> reactive() {
        return Mono.fromFuture(uicTemplate.findByIdFuture(1L));
    }
}
