package me.min.karrotmarket.item.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemCommentCreatePayload {
    @NotBlank
    private String comment;
}
