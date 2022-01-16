package me.min.karrotmarket.item;

import me.min.karrotmarket.item.model.Category;
import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.ItemStatus;
import me.min.karrotmarket.item.payload.ItemCreatePayload;
import me.min.karrotmarket.item.repository.ItemImageRepository;
import me.min.karrotmarket.item.repository.ItemRepository;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.UserRepository;
import me.min.karrotmarket.user.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @BeforeAll
    void initTestData() {
        final Long id = 1L;
        final String email = "hsm012362@gamil.com";
        final String password = "password";
        final String name = "승민";
        final String phoneNumber = "010-3359-1321";
        final String nickname = "Min";

        // when
        User user = User.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .nickname(nickname).build();

        userRepository.save(user);
    }

    @AfterAll
    void deleteUser() {
        userRepository.deleteAll();
    }

    @Test
    void save_item_test() {
        final String title = "test_item_1";
        final Integer price = 1000;
        final Category category = Category.BEAUTY;
        final String description = "test_item_description";
        final List<String> images = List.of(
                "https://source.unsplash.com/user/c_v_r/1900x800",
                "https://source.unsplash.com/user/c_v_r/100x100"
        );
        // given
        final Long userId = 1L;
        final ItemCreatePayload payload = new ItemCreatePayload(title, price, category, description, images);

        // when
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User"));
        System.out.println(user.getId());
        final Item actual = itemRepository.save(Item.of(payload, user));
        // then
        assertThat(actual.getTitle()).isEqualTo(title);
        assertThat(actual.getDescription()).isEqualTo(description);
        assertThat(actual.getPrice()).isEqualTo(price);
        assertThat(actual.getCategory()).isEqualTo(category);
        assertThat(actual.getStatus()).isEqualTo(ItemStatus.SALE);
        assertThat(actual.getImages().size()).isGreaterThan(0);
        assertThat(actual.getUser().getId()).isEqualTo(user.getId());
    }
}
