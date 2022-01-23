package me.min.karrotmarket;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class KarrotMarketApplication implements WebMvcConfigurer {

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
        final SecurityScheme securityScheme
                = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
        final Components components = new Components()
                .addSecuritySchemes("bearer-key", securityScheme);
        return new OpenAPI().components(components);
    }
}
