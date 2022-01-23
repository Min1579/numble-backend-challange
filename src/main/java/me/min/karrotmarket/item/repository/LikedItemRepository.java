package me.min.karrotmarket.item.repository;

import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.LikedItem;
import me.min.karrotmarket.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikedItemRepository extends JpaRepository<LikedItem, Long> {
    boolean existsByUserAndItem(final User user, final Item item);
    void deleteByUserAndItem(final User user, final Item item);

    @Query("select LI from LikedItem LI join fetch LI.item I join fetch I.user join fetch I.images where LI.user = ?1 order by LI.createdAt desc")
    List<LikedItem> findLikedItemsByUser(final User user, final Pageable pageable);
}
