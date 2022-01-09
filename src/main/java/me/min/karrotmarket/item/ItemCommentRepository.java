package me.min.karrotmarket.item;

import me.min.karrotmarket.item.model.ItemComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCommentRepository extends JpaRepository<ItemComment, Long> {
}
