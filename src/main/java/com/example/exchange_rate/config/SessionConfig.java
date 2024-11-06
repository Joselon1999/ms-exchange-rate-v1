package com.example.exchange_rate.config;

import com.example.exchange_rate.dto.domain.ExchangeRate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class SessionConfig {

    @Bean
    public RedisTemplate<String, ExchangeRate> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ExchangeRate> template = new RedisTemplate<String, ExchangeRate>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
