package com.technoirarts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CachedUserRepository userRepository;

    private Stopwatch stopwatch = new Stopwatch();

    @RequestMapping("/users")
    public Model users(Model model) {
        stopwatch.start();
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("elapsed", stopwatch.elapsed());
        model.addAttribute("users", users);
        return model;
    }

    @RequestMapping(value = "/user/{id}")
    @ResponseBody
    public User user(@PathVariable("id") Long id) {
        LOG.info("Loading user {}...", id);
        stopwatch.start();
        User user = userRepository.findOne(id);
        LOG.info("Took about {} ms.", stopwatch.elapsed());
        return user;
    }
}
