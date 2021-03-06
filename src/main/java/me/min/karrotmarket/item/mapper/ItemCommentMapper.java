package me.min.karrotmarket.item.mapper;

import lombok.*;
import me.min.karrotmarket.item.model.ItemComment;
import me.min.karrotmarket.shared.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCommentMapper {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
    private UserMapper user;
    private List<ItemRecommentMapper> recomments;

    @Builder
    public ItemCommentMapper(Long id,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             String comment,
                             UserMapper user,
                             List<ItemRecommentMapper> recomments) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comment = comment;
        this.user = user;
        this.recomments = recomments;
    }

    public static ItemCommentMapper of(final ItemComment comment) {
        final List<ItemRecommentMapper> recomments = comment.getRecomments()
                .stream()
                .map(ItemRecommentMapper::of)
                .collect(Collectors.toList());

        return ItemCommentMapper.builder()
                .id(comment.getId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .comment(comment.getComment())
                .user(UserMapper.of(comment.getUser()))
                .recomments(recomments)
                .build();
    }
}
