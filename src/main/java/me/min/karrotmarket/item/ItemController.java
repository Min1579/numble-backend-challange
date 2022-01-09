package me.min.karrotmarket.item;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.payload.ItemCommentCreatePayload;
import me.min.karrotmarket.item.payload.ItemCreatePayload;
import me.min.karrotmarket.security.Authentication;
import me.min.karrotmarket.security.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/item")
@RestController
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Long> createItem(@Authentication CurrentUser user,
                                           @RequestBody ItemCreatePayload payload) {
        return new ResponseEntity<>(itemService.createItem(user.getId(), payload), HttpStatus.CREATED);
    }

    @PostMapping("{itemId}/comment")
    public ResponseEntity<Long> createItemComment(@Authentication CurrentUser user,
                                                  @PathVariable("itemId") final Long itemId,
                                                  @RequestBody ItemCommentCreatePayload payload) {
        return new ResponseEntity<>(itemService.createItemComment(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

}
