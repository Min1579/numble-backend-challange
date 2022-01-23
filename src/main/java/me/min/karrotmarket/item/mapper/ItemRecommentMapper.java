package me.min.karrotmarket.item.mapper;

import lombok.*;
import me.min.karrotmarket.item.model.ItemComment;
import me.min.karrotmarket.shared.mapper.UserMapper;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemRecommentMapper {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
    private UserMapper user;

    @Builder
    public ItemRecommentMapper(Long id,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt,
                               String comment,
                               UserMapper user) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comment = comment;
        this.user = user;
    }

    public static ItemRecommentMapper of(final ItemComment comment) {
        return ItemRecommentMapper.builder()
                .id(comment.getId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .comment(comment.getComment())
                .user(UserMapper.of(comment.getUser()))
                .build();
    }
}
