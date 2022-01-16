package me.min.karrotmarket.item.payload;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemCommentCreatePayload {
    private String comment;
}
