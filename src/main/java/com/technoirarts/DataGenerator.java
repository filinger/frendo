package com.technoirarts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(DataGenerator.class);

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void generateUsers() {
        LOG.info("Generating users...");

        User user = new User();
        user.name = "Mark";
        user.gender = "Male";
        user.city = "NY";
        userRepository.save(user);

        LOG.info("Users generated successfully.");
    }
}
