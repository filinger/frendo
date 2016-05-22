package com.technoirarts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.function.Supplier;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    // TODO: merge UserRepository and CachedUserRepository into one proxyfied repository?
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CachedUserRepository cachedUserRepository;

    private Stopwatch stopwatch = new Stopwatch();

    @RequestMapping(value = "/user")
    public String showForm(@RequestParam(name = "id", required = false) Long id, Model model) {
        User user = profile("Retrieving single user", () -> cachedUserRepository.findOne(id));
        Map<Long, User> friends = cachedUserRepository.findAll(user);
        model.addAttribute("users", user);
        model.addAttribute("friends", friends);
        model.addAttribute("userRequestObject", new UserRequestObject());
        model.addAttribute("elapsed", stopwatch.elapsed());
        return "user";
    }

    @RequestMapping(value = "/user", params = "findUser")
    public String showUser(@ModelAttribute UserRequestObject userRequestObject, Model model) {
        Specification<User> spec = userRequestObject.buildSpecification();
        Iterable<User> users = profile("Retrieving users by specification", () -> userRepository.findAll(spec));
        Map<Long, User> friends = cachedUserRepository.findAll(users);
        model.addAttribute("users", users);
        model.addAttribute("friends", friends);
        model.addAttribute("elapsed", stopwatch.elapsed());
        return "user";
    }

    @RequestMapping(value = "/user/all")
    public String showAllUsers(Model model) {
        Iterable<User> users = profile("Retrieving all available users", () -> cachedUserRepository.findAll());
        Map<Long, User> friends = cachedUserRepository.findAll(users);
        model.addAttribute("users", users);
        model.addAttribute("friends", friends);
        model.addAttribute("elapsed", stopwatch.elapsed());
        return "user_all";
    }

    private <T> T profile(String description, Supplier<T> operation) {
        stopwatch.start();
        T result = operation.get();
        LOG.info(description + " :: {} ms", stopwatch.elapsed());
        return result;
    }
}
