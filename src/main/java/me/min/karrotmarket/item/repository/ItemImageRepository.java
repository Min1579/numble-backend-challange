package me.min.karrotmarket.item.repository;

import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    void deleteAllByItem(final Item item);
}
