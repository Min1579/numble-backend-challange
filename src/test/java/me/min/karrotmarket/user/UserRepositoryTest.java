package me.min.karrotmarket.user;

import me.min.karrotmarket.user.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    public void create_user_test() {
        // given
        final String email = "hsm012362@gamil.com";
        final String password = "password";
        final String name = "승민";
        final String phoneNumber = "010-3359-1321";
        final String nickname = "Min";

        // when
        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .nickname(nickname).build();

        User actual = userRepository.save(user);

        //then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getCreatedAt()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNotNull();
        assertThat(actual.getDeletedAt()).isNull();
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getNickname()).isEqualTo(nickname);
    }
}
