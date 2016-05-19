package com.technoirarts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisUserConfig {

    @Bean
    public GenericToStringSerializer<Long> longRedisSerializer() {
        return new GenericToStringSerializer<>(Long.class);
    }

    @Bean
    public Jackson2JsonRedisSerializer<User> userRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(User.class);
    }

    @Bean
    @Autowired
    public RedisTemplate<Long, User> redisUserTemplate(RedisConnectionFactory connectionFactory,
                                                       RedisSerializer<Long> longSerializer,
                                                       RedisSerializer<User> userSerializer) {
        RedisTemplate<Long, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(longSerializer);
        redisTemplate.setValueSerializer(userSerializer);
        return redisTemplate;
    }
}
