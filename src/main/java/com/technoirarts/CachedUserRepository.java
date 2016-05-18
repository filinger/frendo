package com.technoirarts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CachedUserRepository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<Long, User> redisTemplate;

    public User findOne(Long id) {
        User cached = redisTemplate.opsForValue().get(id);
        if (cached != null) {
            return cached;
        }

        User saved = userRepository.findOne(id);
        if (saved != null) {
            redisTemplate.opsForValue().set(id, saved);
            return saved;
        }

        return null;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
