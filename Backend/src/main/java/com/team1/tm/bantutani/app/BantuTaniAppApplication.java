package com.team1.tm.bantutani.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableJpaRepositories("com.team1.tm.bantutani.app.repository")
@OpenAPIDefinition(info = @Info(title = "Bantu Tani API", version = "1.0", description = "include API for working with plants and user information")
, security = {@SecurityRequirement(name="Token", scopes = {"JWT","RSA 4096 key","RS512 Algorithm","header"}),@SecurityRequirement(name="Authentication User", scopes = {"USER","ADMIN","EXPERTS","FARMER","DISTRIBUTOR","SALES"}), @SecurityRequirement(name = "Certificate", scopes = "https certificate")})
public class BantuTaniAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BantuTaniAppApplication.class, args);
	}

}
