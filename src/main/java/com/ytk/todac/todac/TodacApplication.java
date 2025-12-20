package com.ytk.todac.todac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodacApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodacApplication.class, args);
	}

}
