package me.min.karrotmarket.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.shared.BaseEntity;
import me.min.karrotmarket.user.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_comment_id")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemImage item;
}
