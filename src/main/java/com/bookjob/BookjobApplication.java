package com.bookjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookjobApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookjobApplication.class, args);
    }

}
