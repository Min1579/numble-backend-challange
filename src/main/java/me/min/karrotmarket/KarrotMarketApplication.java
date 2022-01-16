package me.min.karrotmarket;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.user.UserRepository;
import me.min.karrotmarket.user.model.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class KarrotMarketApplication implements ApplicationRunner {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(KarrotMarketApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
