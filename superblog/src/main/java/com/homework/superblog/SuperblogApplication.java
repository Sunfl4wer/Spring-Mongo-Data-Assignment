package com.homework.superblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.homework.superblog.common.ActionAspect;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SuperblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperblogApplication.class, args);
	}

	@Bean
	public ActionAspect actionAspect() {
	  return new ActionAspect();
	}

}
