package me.min.karrotmarket.item.repository;

import me.min.karrotmarket.item.model.Category;
import me.min.karrotmarket.item.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategory(final Category category, Pageable pageable);
}
