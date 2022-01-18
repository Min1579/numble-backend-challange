package me.min.karrotmarket.item.repository;

import me.min.karrotmarket.item.model.Category;
import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategoryOrderByCreatedAtDesc(final Category category, Pageable pageable);
    List<Item> findAllByUserOrderByCreatedAtDesc(final User user, Pageable pageable);
}
