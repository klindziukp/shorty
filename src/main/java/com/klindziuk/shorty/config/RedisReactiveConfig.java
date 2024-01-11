/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.config;

import com.klindziuk.shorty.model.repository.LinkEntity;
import com.klindziuk.shorty.model.response.LinkResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnProperty(name = "com.klindziuk.shorty.cache.enabled", havingValue = "true")
public class RedisReactiveConfig {

  @Bean
  public ReactiveHashOperations<String, Long, LinkResponse> hashOperations(
      ReactiveRedisConnectionFactory redisConnectionFactory) {

    return new ReactiveRedisTemplate<>(
            redisConnectionFactory,
            RedisSerializationContext.<String, LinkEntity>newSerializationContext(
                    new StringRedisSerializer())
                .hashKey(new GenericToStringSerializer<>(Long.class))
                .hashValue(new Jackson2JsonRedisSerializer<>(LinkResponse.class))
                .build())
        .opsForHash();
  }
}
