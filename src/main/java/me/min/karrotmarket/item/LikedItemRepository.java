package me.min.karrotmarket.item;

import me.min.karrotmarket.item.model.LikedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedItemRepository extends JpaRepository<LikedItem, Long> {
}
