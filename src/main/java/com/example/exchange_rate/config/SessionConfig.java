package com.example.exchange_rate.config;

import com.example.exchange_rate.dto.ExchangeRateResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class SessionConfig {

    @Bean
    public RedisTemplate<String, ExchangeRateResponse> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ExchangeRateResponse> template = new RedisTemplate<String, ExchangeRateResponse>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
