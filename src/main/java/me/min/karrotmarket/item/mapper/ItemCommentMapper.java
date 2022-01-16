package me.min.karrotmarket.item.mapper;

import lombok.Builder;
import lombok.Data;
import me.min.karrotmarket.item.model.ItemComment;
import me.min.karrotmarket.shared.mapper.UserMapper;

import java.time.LocalDateTime;

@Data
public class ItemCommentMapper {
    private Long id;
    private LocalDateTime createdAt;
    private String comment;
    private UserMapper user;

    @Builder
    public ItemCommentMapper(Long id, LocalDateTime createdAt, String comment, UserMapper user) {
        this.id = id;
        this.createdAt = createdAt;
        this.comment = comment;
        this.user = user;
    }

    public static ItemCommentMapper of(final ItemComment comment) {
        return ItemCommentMapper.builder()
                .id(comment.getId())
                .createdAt(comment.getCreatedAt())
                .comment(comment.getComment())
                .user(UserMapper.of(comment.getUser()))
                .build();
    }
}
