package com.example.classhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClassHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassHubApplication.class, args);
    }

}
