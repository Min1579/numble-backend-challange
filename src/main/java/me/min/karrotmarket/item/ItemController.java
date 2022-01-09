package me.min.karrotmarket.item;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.mapper.ItemCommentMapper;
import me.min.karrotmarket.item.payload.ItemCommentCreatePayload;
import me.min.karrotmarket.item.payload.ItemCommentUpdatePayload;
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

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/item")
@RestController
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Long> createItem(@Authentication final CurrentUser user,
                                           @RequestBody ItemCreatePayload payload) {
        return new ResponseEntity<>(itemService.createItem(user.getId(), payload), HttpStatus.CREATED);
    }

    @PostMapping("{itemId}/comment")
    public ResponseEntity<Long> createItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("itemId") final Long itemId,
                                                  @RequestBody final ItemCommentCreatePayload payload) {
        return new ResponseEntity<>(itemService.createItemComment(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @GetMapping("{itemId}/comments/{page}/{size}")
    public ResponseEntity<List<ItemCommentMapper>> findCommentsByItemId(@PathVariable("itemId") final Long itemId,
                                                                        @PathVariable("page") final int page,
                                                                        @PathVariable("size") final int size) {
        return ResponseEntity.ok(this.itemService.findCommentsByItemId(itemId, page, size));
    }

    @PutMapping("comment/{commentId}")
    public ResponseEntity<Void> updateItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("commentId") final Long commentId,
                                                  @RequestBody final ItemCommentUpdatePayload payload) {
        itemService.updateItemComment(user.getId(), commentId, payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<Void> updateItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("commentId") final Long commentId) {
        itemService.deleteComment(user.getId(), commentId);
        return ResponseEntity.ok().build();
    }
}
