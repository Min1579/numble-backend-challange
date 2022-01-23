package me.min.karrotmarket.item.mapper;

import lombok.*;
import me.min.karrotmarket.item.model.Category;
import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.ItemImage;
import me.min.karrotmarket.item.model.ItemStatus;
import me.min.karrotmarket.shared.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDetailMapper {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private int price;
    private Category category;
    private String description;
    private ItemStatus status;
    private int likeCount;
    private int commentCount;
    private UserMapper user;
    private List<String> images = new ArrayList<>();

    private boolean liked;

    @Builder
    public ItemDetailMapper(Long id,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            String title,
                            int price,
                            Category category,
                            String description,
                            ItemStatus status,
                            int likeCount,
                            int commentCount,
                            UserMapper user,
                            List<String> images,
                            boolean liked) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.status = status;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.user = user;
        this.images = images;
        this.liked = liked;
    }

    public static ItemDetailMapper of(final Item item, final boolean liked) {
        return ItemDetailMapper.builder()
                .id(item.getId())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .title(item.getTitle())
                .price(item.getPrice())
                .liked(liked)
                .category(item.getCategory())
                .status(item.getStatus())
                .description(item.getDescription())
                .likeCount(item.getLikeCount())
                .commentCount(item.getCommentCount())
                .images(item.getImages().stream().map(ItemImage::getImage).collect(Collectors.toList()))
                .user(UserMapper.of(item.getUser()))
                .build();
    }
}
