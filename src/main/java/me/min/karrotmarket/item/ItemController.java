package me.min.karrotmarket.item;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.mapper.ItemCommentMapper;
import me.min.karrotmarket.item.payload.*;
import me.min.karrotmarket.item.service.ItemCommentService;
import me.min.karrotmarket.item.service.ItemService;
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
    private final ItemCommentService itemCommentService;

    @PostMapping
    public ResponseEntity<Long> createItem(@Authentication final CurrentUser user,
                                           @RequestBody ItemCreatePayload payload) {
        return new ResponseEntity<>(itemService.createItem(user.getId(), payload), HttpStatus.CREATED);
    }

    @PatchMapping("{itemId}")
    public ResponseEntity<Long> updateItemStatus(@Authentication final CurrentUser user,
                                                 @PathVariable("itemId") final Long itemId,
                                                 @RequestBody ItemStatusUpdatePayload payload) {
        return new ResponseEntity<>(itemService.updateItemStatus(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @PutMapping("{itemId}")
    public ResponseEntity<Long> updateItem(@Authentication final CurrentUser user,
                                           @PathVariable("itemId") final Long itemId,
                                           @RequestBody ItemUpdatePayload payload) {
        return new ResponseEntity<>(itemService.updateItem(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @DeleteMapping("{itemId}")
    public void deleteItem(@Authentication final CurrentUser user,
                           @PathVariable("itemId") final Long itemId) {
        this.itemService.deleteItem(user.getId(), itemId);
    }

    @PostMapping("{itemId}/comment")
    public ResponseEntity<Long> createItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("itemId") final Long itemId,
                                                  @RequestBody final ItemCommentCreatePayload payload) {
        return new ResponseEntity<>(itemCommentService.createItemComment(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @GetMapping("{itemId}/comments/{page}/{size}")
    public ResponseEntity<List<ItemCommentMapper>> findCommentsByItemId(@PathVariable("itemId") final Long itemId,
                                                                        @PathVariable("page") final int page,
                                                                        @PathVariable("size") final int size) {
        return ResponseEntity.ok(this.itemCommentService.findCommentsByItemId(itemId, page, size));
    }

    @PutMapping("comment/{commentId}")
    public ResponseEntity<Void> updateItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("commentId") final Long commentId,
                                                  @RequestBody final ItemCommentUpdatePayload payload) {
        itemCommentService.updateItemComment(user.getId(), commentId, payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<Void> updateItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("commentId") final Long commentId) {
        itemCommentService.deleteComment(user.getId(), commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{itemId}/like")
    public ResponseEntity<Void> likeItem(@Authentication final CurrentUser user,
                                         @PathVariable("itemId") final Long itemId) {
        itemService.likeItem(user.getId(), itemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{itemId}/like")
    public ResponseEntity<Void> cancelLikeItem(@Authentication final CurrentUser user,
                                               @PathVariable("itemId") final Long itemId) {
        itemService.cancelLikeItem(user.getId(), itemId);
        return ResponseEntity.ok().build();
    }
}
