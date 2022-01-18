package me.min.karrotmarket.item.repository;

import me.min.karrotmarket.item.model.Category;
import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.ItemStatus;
import me.min.karrotmarket.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategoryAndStatusIsNotOrderByCreatedAtDesc(final Category category, final ItemStatus status, final Pageable pageable);
    List<Item> findAllByUserAndStatusIsNotOrderByCreatedAtDesc(final User user, final ItemStatus status, final Pageable pageable);
    List<Item> findAllByUserAndStatusOrderByCreatedAtDesc(final User user, final ItemStatus status, final Pageable pageable);
    @Query("select I from Item I where I.status in (?2) and I.id in (select LI.item.id from LikedItem LI where LI.user = ?1) order by I.createdAt desc")
    List<Item> findAllMyLikedItem(final User user, final List<ItemStatus> status, final Pageable pageable);
}
