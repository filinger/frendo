package com.technoirarts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CachedUserRepository cachedUserRepository;

    private Stopwatch stopwatch = new Stopwatch();

    @RequestMapping(value = "/user")
    public String showForm(Model model) {
        stopwatch.start();
        model.addAttribute("userRequestObject", new UserRequestObject());
        model.addAttribute("elapsed", stopwatch.elapsed());
        return "user";
    }

    @RequestMapping(value = "/user", params = "findUser")
    public String showUser(@ModelAttribute UserRequestObject userRequestObject, Model model) {
        LOG.info("Loading user {}...", userRequestObject.getId());
        stopwatch.start();
        model.addAttribute("users", userRepository.findAll(UserSpecifications.findUsers(
            userRequestObject.getId(),
            userRequestObject.getSurname(),
            userRequestObject.getName(),
            userRequestObject.getAge(),
            userRequestObject.getCity(),
            userRequestObject.getExtra()
        )));
        LOG.info("Took about {} ms.", stopwatch.elapsed());
        return "user";
    }

    @RequestMapping(value = "/user/{id}")
    @ResponseBody
    public User user(@PathVariable("id") Long id) {
        LOG.info("Loading user {}...", id);
        stopwatch.start();
        User user = cachedUserRepository.findOne(id);
        LOG.info("Took about {} ms.", stopwatch.elapsed());
        return user;
    }

    @RequestMapping(value = "/user/all")
    public String showAllUsers(Model model) {
        stopwatch.start();
        Iterable<User> users = cachedUserRepository.findAll();
        model.addAttribute("elapsed", stopwatch.elapsed());
        model.addAttribute("users", users);
        return "user_all";
    }

}
