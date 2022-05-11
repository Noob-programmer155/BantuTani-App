package com.team1.tm.bantutani.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableJpaRepositories("com.team1.tm.bantutani.app.repository")
public class BantuTaniAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BantuTaniAppApplication.class, args);
	}

}
