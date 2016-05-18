package com.technoirarts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    private Stopwatch stopwatch = new Stopwatch();

    @RequestMapping(value="/user/all")
    public String showAllUsers(Model model) {
        stopwatch.start();
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("elapsed", stopwatch.elapsed());
        model.addAttribute("users", users);
        return "users_all";
    }

    @RequestMapping(value="/user")
    public String showForm(Model model) {
        stopwatch.start();
        model.addAttribute("user", new User());
        model.addAttribute("elapsed", stopwatch.elapsed());
        return "users";
    }

    @RequestMapping(value="/user", params="findUser")
    public String showUser(@ModelAttribute User user, Model model) {
        LOG.info("Loading user {}...", user.getId());
        stopwatch.start();
        model.addAttribute("users", userRepository.findOne(user.getId()));
        LOG.info("Took about {} ms.", stopwatch.elapsed());
        return "users";
    }

}
