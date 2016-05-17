package com.technoirarts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class RepositoryDataGenerator {

    private static final String[] PREFIXES = {"Kr", "Ca", "Ra", "Mrok", "Cru",
        "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
        "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
        "Mar", "Luk"};
    private static final String[] SUFFIXES = {"air", "ir", "mi", "sor", "mee", "clo",
        "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
        "marac", "zoir", "slamar", "salmar", "urak"};
    private static final String[] POSTFIXES = {"d", "ed", "ark", "arc", "es", "er", "der",
        "tron", "med", "ure", "zur", "cred", "mur"};

    private static final Logger LOG = LoggerFactory.getLogger(RepositoryDataGenerator.class);
    private static final Random RAND = new Random();

    @Value("${frendo.generator.userAmount}")
    private Integer userAmount;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void generateUsers() {
        LOG.info("Generating {} users...", userAmount);
        long startTime = System.nanoTime();
        for (int i = 0; i < userAmount; ++i) {
            userRepository.save(generateRandomUser());
        }
        long elapsedTime = System.nanoTime() - startTime;
        LOG.info("Users generated successfully, took about {} ms.", elapsedTime / 1000000);
    }

    private User generateRandomUser() {
        String name = getRandomName() + " " + getRandomName();
        String gender = RAND.nextBoolean() ? "Male" : "Female";
        String city = getRandomName();
        return new User(null, name, gender, city);
    }

    private String getRandomName() {
        return getRandomItem(PREFIXES)
            + getRandomItem(SUFFIXES)
            + getRandomItem(POSTFIXES);
    }

    private <T> T getRandomItem(T[] items) {
        return items[RAND.nextInt(items.length)];
    }
}
