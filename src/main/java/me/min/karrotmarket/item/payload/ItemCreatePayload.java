package me.min.karrotmarket.item.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.item.model.Category;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemCreatePayload {
    @NotBlank
    private String title;
    @NotBlank
    private Integer price;
    @NotBlank
    private Category category;
    @NotBlank
    private String description;
    private List<String> images;
}
