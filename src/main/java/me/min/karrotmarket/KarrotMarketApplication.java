package me.min.karrotmarket;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.user.UserRepository;
import me.min.karrotmarket.user.model.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.beans.BeanProperty;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class KarrotMarketApplication implements ApplicationRunner, WebMvcConfigurer {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(KarrotMarketApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")
                .maxAge(3000);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final Components components = new Components()
                .addSecuritySchemes("bearer-key",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));
        return new OpenAPI().components(components);
    }

    @BeanProperty

    @Override
    public void run(ApplicationArguments args) {
        final User user = User.builder()
                .email("hsm012362@gmail.com")
                .password("$2a$10$xDFCfHptj7Axu56fYzqmpusZs8yz3gf..ePCVSrIjW2gvETqETv2a")
                .nickname("min")
                .name("min")
                .phoneNumber("010-3359-1321")
                .build();
        userRepository.save(user);
    }
}
