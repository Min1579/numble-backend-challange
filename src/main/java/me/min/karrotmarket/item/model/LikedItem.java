package me.min.karrotmarket.item.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.shared.BaseEntity;
import me.min.karrotmarket.user.model.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "likedItem_userId_itemId_unique_constraint", columnNames = { "user_id", "item_id" }) })
public class LikedItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public LikedItem(Long id, User user, Item item) {
        this.id = id;
        this.user = user;
        this.item = item;
    }

    public static LikedItem of(final User user, final Item item) {
        return LikedItem.builder()
                .item(item)
                .user(user)
                .build();
    }
}
