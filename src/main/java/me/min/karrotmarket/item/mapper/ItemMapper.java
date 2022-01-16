package me.min.karrotmarket.item.mapper;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class ItemMapper {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private Integer price;
    private Category category;
    private String description;
    private ItemStatus status;
    private Integer likeCount;
    private Integer commentCount;
    private UserMapper user;
    private List<String> images = new ArrayList<>();

    @Builder
    public ItemMapper(Long id,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt,
                      String title,
                      Integer price,
                      Category category,
                      String description,
                      ItemStatus status,
                      Integer likeCount,
                      Integer commentCount,
                      UserMapper user,
                      List<String> images) {
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
    }

    public static ItemMapper of(final Item item) {
        return ItemMapper.builder()
                .id(item.getId())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .title(item.getTitle())
                .price(item.getPrice())
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
