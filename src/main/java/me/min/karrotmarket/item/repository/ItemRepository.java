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
    @Query("select I from Item I join fetch I.images join fetch I.user where I.category = ?1 and I.status <> 'DELETED' order by I.createdAt desc")
    List<Item> findAllByCategoryOrderByCreatedAtDesc(final Category category, final Pageable pageable);

    @Query("select I from Item I join fetch I.images join fetch I.user where I.user = ?1 and I.status <> 'DELETED' order by I.createdAt desc")
    List<Item> findAllByUserOrderByCreatedAtDesc(final User user, final Pageable pageable);

    @Query("select I from Item I join fetch I.images join I.user where I.user = ?1 and I.status = ?2 order by I.createdAt desc")
    List<Item> findAllByUserAndStatusOrderByCreatedAtDesc(final User user, final ItemStatus status, final Pageable pageable);

    @Query("select distinct I from Item I join fetch I.images join fetch I.user where I.status <> 'DELETED' order by I.createdAt desc")
    List<Item> findItemsOrderByCreatedAtDesc(final Pageable pageable);
}
