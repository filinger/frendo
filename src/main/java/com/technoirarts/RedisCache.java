package com.technoirarts;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RedisCache {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public GenericToStringSerializer<Long> longRedisSerializer() {
        return new GenericToStringSerializer<>(Long.class);
    }

    @Bean
    public Jackson2JsonRedisSerializer<User> userRedisSerializer() {
        Jackson2JsonRedisSerializer<User> userSerializer = new Jackson2JsonRedisSerializer<>(User.class);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("FrendoModule");
        module.addSerializer(Friend.class, new FriendSerializer());
        module.addDeserializer(Friend.class, new FriendDeserializer());
        mapper.registerModule(module);
        userSerializer.setObjectMapper(mapper);
        return userSerializer;
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

    public class FriendSerializer extends JsonSerializer<Friend> {

        @Override
        public void serialize(Friend value, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeStartObject();
            generator.writeNumberField("id", value.getId());
            generator.writeNumberField("userId", value.getUser().getId());
            generator.writeEndObject();
        }
    }

    public class FriendDeserializer extends JsonDeserializer<Friend> {

        @Override
        public Friend deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            JsonNode node = parser.getCodec().readTree(parser);
            Long id = node.get("id").longValue();
            Long userId = node.get("userId").longValue();
            // TODO: user should be retrieved from cache as well
            User user = userRepository.findOne(userId);
            return new Friend(id, user);
        }
    }
}
