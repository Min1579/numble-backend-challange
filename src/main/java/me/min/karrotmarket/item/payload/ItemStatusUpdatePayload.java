package me.min.karrotmarket.item.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.item.model.ItemStatus;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemStatusUpdatePayload {
    @NotBlank
    private ItemStatus status;
}
