package me.min.karrotmarket.item.repository;

import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.ItemComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCommentRepository extends JpaRepository<ItemComment, Long> {
    List<ItemComment> findItemCommentsByItemOrderByCreatedAtAsc(final Item item, Pageable pageable);
}
