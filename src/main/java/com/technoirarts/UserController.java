package com.technoirarts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private Stopwatch stopwatch = new Stopwatch();

    @RequestMapping("/users")
    public Model users(Model model) {
        stopwatch.start();
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("elapsed", stopwatch.elapsed());
        model.addAttribute("users", users);
        return model;
    }

}
