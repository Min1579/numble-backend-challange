package me.min.karrotmarket.item.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.item.payload.ItemCreatePayload;
import me.min.karrotmarket.shared.BaseEntity;
import me.min.karrotmarket.user.model.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, columnDefinition = "SALE")
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Item(Long id, String title, Integer price, Category category, String description, ItemStatus status, User user) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.status = status;
        this.user = user;
    }

    public static Item of(final ItemCreatePayload payload, final User user) {
        return Item.builder()
                .title(payload.getTitle())
                .price(payload.getPrice())
                .category(payload.getCategory())
                .description(payload.getDescription())
                .user(user)
                .build();
    }
}
