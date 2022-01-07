package me.min.karrotmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KarrotMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(KarrotMarketApplication.class, args);
    }

}
