package com.masoud.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = {"com.masoud.*"})
@EntityScan(basePackages = {"com.masoud.dataaccess.entity"})
@EnableJpaRepositories(basePackages = "com.masoud.dataaccess.repository")
@OpenAPIDefinition(info = @Info(title = "onlineShop API",version = "1.0",description = "java spring boot onlineShop"))

public class AppApplication {


    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
