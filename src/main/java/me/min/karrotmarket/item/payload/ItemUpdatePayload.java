package me.min.karrotmarket.item.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.item.model.Category;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemUpdatePayload {
    private String title;
    private Integer price;
    private Category category;
    private String description;
    private List<String> images;
}
