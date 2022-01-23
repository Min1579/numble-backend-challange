package me.min.karrotmarket.item.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.item.payload.ItemCommentCreatePayload;
import me.min.karrotmarket.item.payload.ItemCommentUpdatePayload;
import me.min.karrotmarket.shared.BaseEntity;
import me.min.karrotmarket.user.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ItemComment parent;

    @OneToMany(mappedBy = "parent")
    private List<ItemComment> recomments = new ArrayList<>();

    @Builder
    public ItemComment(Long id, String comment, User user, Item item, ItemComment parent, List<ItemComment> recomments) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.item = item;
        this.parent = parent;
        this.recomments = recomments;
    }

    public static ItemComment of(final User user, final Item item, final ItemCommentCreatePayload payload) {
        return ItemComment.builder()
                .user(user)
                .item(item)
                .comment(payload.getComment())
                .build();
    }

    public static ItemComment of(final User user, final ItemComment parent, final String comment) {
        return ItemComment.builder()
                .user(user)
                .parent(parent)
                .comment(comment)
                .build();
    }

    public ItemComment addRecomment(final ItemComment itemComment) {
        this.getRecomments().add(itemComment);
        return this;
    }

    public ItemComment updateComment(final ItemCommentUpdatePayload payload) {
        this.comment = payload.getComment();
        return this;
    }
}
