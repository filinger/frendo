package com.technoirarts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public Model users(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return model;
    }
}
