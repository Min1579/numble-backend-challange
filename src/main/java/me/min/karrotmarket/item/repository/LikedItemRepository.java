package me.min.karrotmarket.item.repository;

import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.LikedItem;
import me.min.karrotmarket.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedItemRepository extends JpaRepository<LikedItem, Long> {
    boolean existsByUserAndItem(final User user, final Item item);
    void deleteByUserAndItem(final User user, final Item item);
}
