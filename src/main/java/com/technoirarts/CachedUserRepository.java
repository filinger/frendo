package com.technoirarts;

import org.apache.solr.client.solrj.SolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Repository
public class CachedUserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CachedUserRepository.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SearchableSolrRepository<SolrUser> searchableUserRepository;

    @Autowired
    private RedisTemplate<Long, User> redisTemplate;

    @Value("${frendo.cache.retryTimeoutMillis}")
    private Integer retryTimeoutMillis;

    private boolean redisAvailable = true;
    private Stopwatch retryStopwatch = new Stopwatch();

    @Bean
    public SearchableSolrRepository<SolrUser> searchableSolrRepository(SolrServer solrServer) {
        SolrTemplate solrTemplate = new SolrTemplate(solrServer);
        solrTemplate.afterPropertiesSet();
        return new SimpleSearchableSolrRepository<>(solrTemplate, SolrUser.class);
    }

    public User findOne(Long id) {
        if (id == null) {
            return null;
        }

        User cached = tryGetCachedUser(id);
        if (cached != null) {
            return cached;
        }

        User saved = userRepository.findOne(id);
        if (saved != null) {
            trySetCachedUser(id, saved);
            return saved;
        }

        return null;
    }

    // TODO: cache all users via Redis scan/keys iterator?
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    private List<User> findAll(Iterable<Long> ids) {
        List<User> users = new ArrayList<>();
        for (Long id : ids) {
            User user = findOne(id);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public List<User> findAll(UserRequestObject userRequestObject) {
        Iterable<SolrUser> solrUsers = searchableUserRepository.search(userRequestObject.buildCriteria());
        List<User> users = new ArrayList<>();
        for (SolrUser solrUser : solrUsers) {
            Long userId = solrUser.getUserId();
            User user = findOne(userId);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public Map<Long, User> findFriends(User user) {
        Map<Long, User> friends = new HashMap<>();
        if (user == null) {
            return friends;
        }
        for (Long fid : user.getFriendIds()) {
            friends.put(fid, findOne(fid));
        }
        return friends;
    }

    public Map<Long, User> findFriends(Iterable<User> users) {
        Map<Long, User> friends = new HashMap<>();
        if (users == null) {
            return friends;
        }
        for (User user : users) {
            for (Long fid : user.getFriendIds()) {
                friends.put(fid, findOne(fid));
            }
        }
        return friends;
    }

    private User tryGetCachedUser(Long id) {
        return (User) tryRedisOps(() -> redisTemplate.opsForValue().get(id));
    }

    private void trySetCachedUser(final Long id, final User user) {
        tryRedisOps(() -> redisTemplate.opsForValue().set(id, user));
    }

    private void tryRedisOps(Runnable ops) {
        tryRedisOps(() -> {
            ops.run();
            return null;
        });
    }

    private Object tryRedisOps(Supplier<Object> ops) {
        try {
            if (redisAvailable || retryStopwatch.elapsed() > retryTimeoutMillis) {
                Object result = ops.get();
                redisAvailable = true;
                return result;
            }
        } catch (RedisConnectionFailureException e) {
            redisAvailable = false;
            retryStopwatch.start();
            LOG.warn("Error performing redis operation. {}", e.getMessage());
            LOG.warn("Cache resolving will be disabled for the next {} ms", retryTimeoutMillis);
        }
        return null;
    }
}
