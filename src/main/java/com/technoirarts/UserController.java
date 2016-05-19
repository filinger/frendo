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
    public String showForm(@RequestParam(name = "id", required = false) Long id, Model model) {
        stopwatch.start();
        model.addAttribute("users", cachedUserRepository.findOne(id));
        model.addAttribute("userRequestObject", new UserRequestObject());
        model.addAttribute("elapsed", stopwatch.elapsed());
        LOG.info("Took about {} ms.", stopwatch.elapsed());
        return "user";
    }

    @RequestMapping(value = "/user", params = "findUser")
    public String showUser(@ModelAttribute UserRequestObject userRequestObject, Model model) {
        stopwatch.start();
        model.addAttribute("users", userRepository.findAll(UserSpecifications.findUsers(
            userRequestObject.getSurname(),
            userRequestObject.getName(),
            userRequestObject.getAge(),
            userRequestObject.getCity(),
            userRequestObject.getExtra()
        )));
        model.addAttribute("elapsed", stopwatch.elapsed());
        LOG.info("Took about {} ms.", stopwatch.elapsed());
        return "user";
    }

    @RequestMapping(value = "/user/all")
    public String showAllUsers(Model model) {
        stopwatch.start();
        model.addAttribute("users", cachedUserRepository.findAll());
        model.addAttribute("elapsed", stopwatch.elapsed());
        LOG.info("Took about {} ms.", stopwatch.elapsed());
        return "user_all";
    }

}
