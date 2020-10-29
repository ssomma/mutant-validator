package com.dna.tester.mutantvalidator;

import com.dna.tester.mutantvalidator.configuration.AppConfiguration;
import com.dna.tester.mutantvalidator.model.DNAStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class MutantValidatorApplication {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	RedisTemplate<String, DNAStat> redisTemplate() {
		RedisTemplate<String, DNAStat> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

	private static final Logger log = LoggerFactory.getLogger(MutantValidatorApplication.class);
	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(MutantValidatorApplication.class, args);
		AppConfiguration appConfiguration = context.getBean(AppConfiguration.class);

	}



}
