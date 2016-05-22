package com.technoirarts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private SolrUserRepository solrUserRepository;

    private Stopwatch stopwatch = new Stopwatch();

    @PostConstruct
    private void generateAndSave() {
        LOG.info("Generating {} users...", userAmount);
        stopwatch.start();
        List<User> users = generateUsers();
        List<SolrUser> solrUsers = generateSolrUsers(users);
        generateFriends(users);
        LOG.info("Users generated successfully, took about {} ms.", stopwatch.elapsed());

        saveToDB(users);
        saveToSolr(solrUsers);
    }

    private List<SolrUser> generateSolrUsers(List<User> users) {
        List<SolrUser> solrUsers = new ArrayList<>(userAmount);
        for (User user : users) {
            SolrUser solrUser = SolrUser.fromUser(user);
            solrUsers.add(solrUser);
        }
        return solrUsers;
    }

    private List<User> generateUsers() {
        List<User> users = new ArrayList<>(userAmount);
        for (int i = 0; i < userAmount; ++i) {
            User user = generateRandomUser();
            user.setId((long) i);
            users.add(user);
        }
        return users;
    }

    private void generateFriends(List<User> users) {
        for (User user : users) {
            for (int i = 0; i < (RAND.nextInt(5) + 3); i++) {
                user.getFriendIds().add((long) RAND.nextInt(userAmount));
            }
        }
    }

    private void saveToSolr(List<SolrUser> solrUsers) {
        LOG.info("Saving to Solr...");
        stopwatch.start();
        solrUserRepository.deleteAll();
        solrUserRepository.save(solrUsers);
        LOG.info("Saved, took about {} ms.", stopwatch.elapsed());
    }

    private void saveToDB(List<User> users) {
        LOG.info("Saving to DB...");
        stopwatch.start();
        userRepository.deleteAll();
        userRepository.save(users);
        LOG.info("Saved, took about {} ms.", stopwatch.elapsed());
    }

    private User generateRandomUser() {
        String surname = getRandomName();
        String name = getRandomName();
        Integer age = (RAND.nextInt(61) + 10);
        String city = getRandomName();
        String extra = getRandomName() + getRandomName() + getRandomName();
        List<Long> friendIds = new ArrayList<>();
        return new User(null, surname, name, age, city, extra, friendIds);
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
