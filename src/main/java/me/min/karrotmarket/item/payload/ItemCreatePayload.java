package me.min.karrotmarket.item.payload;

import lombok.Data;
import me.min.karrotmarket.item.model.Category;

import java.util.List;

@Data
public class ItemCreatePayload {
    private String title;
    private Integer price;
    private Category category;
    private String description;
    private List<String> images;

    public ItemCreatePayload(String title, Integer price, Category category, String description, List<String> images) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.images = images;
    }
}
