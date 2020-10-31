package com.dna.tester.mutantvalidator;

import com.dna.tester.mutantvalidator.configuration.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableCaching
public class MutantValidatorApplication {

	private static final Logger log = LoggerFactory.getLogger(MutantValidatorApplication.class);
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MutantValidatorApplication.class, args);
		AppConfiguration appConfiguration = context.getBean(AppConfiguration.class);
	}



}
