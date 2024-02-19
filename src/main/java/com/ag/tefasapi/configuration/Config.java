package com.ag.tefasapi.configuration;

import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@EnableCaching
public class Config {

	// Redis client
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName("localhost"); // Set your Redis host
		redisConfig.setPort(6379); // Set your Redis port
		return new JedisConnectionFactory(redisConfig);
	}

	// we will use Redis as caching manager(spring boot interceptor for caching)
	@Bean
	public RedisCacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
		RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofDays(1)) // Set cache expiration time
				.disableCachingNullValues(); // Disable caching of null values

		return RedisCacheManager.builder(jedisConnectionFactory)
				.cacheDefaults(cacheConfiguration)
				.build();
	}
}
