package me.min.karrotmarket.item.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemImage(Long id, String image, Item item) {
        this.id = id;
        this.image = image;
        this.item = item;
    }

    public static ItemImage of(final String image, final Item item) {
        return ItemImage.builder()
                .image(image)
                .item(item).build();
    }
}
