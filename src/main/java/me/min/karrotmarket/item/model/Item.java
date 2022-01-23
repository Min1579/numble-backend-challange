package me.min.karrotmarket.item.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.item.payload.ItemCreatePayload;
import me.min.karrotmarket.item.payload.ItemUpdatePayload;
import me.min.karrotmarket.shared.BaseEntity;
import me.min.karrotmarket.user.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = @Index(name = "ix_createdAt_itemStatus_category", columnList = "created_at,item_status,category"))
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status", nullable = false)
    private ItemStatus status = ItemStatus.SALE;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> images = new ArrayList<>();

    @Builder
    public Item(Long id,
                String title,
                Integer price,
                Category category,
                String description,
                ItemStatus status,
                Integer likeCount,
                Integer commentCount,
                User user,
                List<ItemImage> images) {
        this.id = id;
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

    public Item incrLikeCount() {
        this.likeCount++;
        return this;
    }

    public Item descLikeCount() {
        this.likeCount--;
        return this;
    }

    public Item incrCommentCount() {
        this.commentCount++;
        return this;
    }

    public Item descCommentCount() {
        this.commentCount--;
        return this;
    }

    public Item updateItemStatus(final ItemStatus status) {
        this.status = status;
        return this;
    }

    public Item updateItem(ItemUpdatePayload payload) {
        this.title = payload.getTitle();
        this.description = payload.getDescription();
        this.category = payload.getCategory();
        this.price = payload.getPrice();
        if (payload.getImages().size() > 0) {
            this.images = payload.getImages().stream()
                    .map((image) -> ItemImage.of(image, this))
                    .collect(Collectors.toList());
        }
        return this;
    }

    public static Item of(final ItemCreatePayload payload, final User user) {
        return Item.builder()
                .title(payload.getTitle())
                .price(payload.getPrice())
                .category(payload.getCategory())
                .description(payload.getDescription())
                // TODO: set default enum
                .status(ItemStatus.SALE)
                // TODO: set default comment count
                .commentCount(0)
                // TODO: set default like count
                .likeCount(0)
                .user(user)
                .build();
    }
}

