package me.min.karrotmarket.item.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.item.model.ItemStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemStatusUpdatePayload {
    private ItemStatus status;
}
