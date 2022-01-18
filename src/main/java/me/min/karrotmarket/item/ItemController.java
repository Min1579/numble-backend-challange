package me.min.karrotmarket.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.mapper.ItemCommentMapper;
import me.min.karrotmarket.item.mapper.ItemMapper;
import me.min.karrotmarket.item.model.Category;
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

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/item")
@RestController
public class ItemController {
    private final ItemService itemService;
    private final ItemCommentService itemCommentService;

    @GetMapping("{itemId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<ItemMapper> findItemById(@PathVariable("itemId") final Long itemId) {
        return ResponseEntity.ok(itemService.findItemByRequestItemId(itemId));
    }

    @GetMapping("{page}/{size}/{category}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<List<ItemMapper>> findAllItemByCategory(@PathVariable("page") final int page,
                                                                  @PathVariable("size") final int size,
                                                                  @PathVariable("category") Category category) {
        return ResponseEntity.ok(itemService.findItemsByCategory(page, size, category));
    }

    @GetMapping("{page}/{size}/{userId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<List<ItemMapper>> findItemsByUser(@PathVariable("page") final int page,
                                                            @PathVariable("size") final int size,
                                                            @PathVariable("userId") final Long userId) {
        return ResponseEntity.ok(itemService.findAllItemByUser(page, size, userId));
    }

    @PostMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<ItemMapper> createItem(@Authentication final CurrentUser user,
                                           @Valid @RequestBody ItemCreatePayload payload) {
        return new ResponseEntity<>(itemService.createItem(user.getId(), payload), HttpStatus.CREATED);
    }

    @PatchMapping("{itemId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<ItemMapper> updateItemStatus(@Authentication final CurrentUser user,
                                                 @PathVariable("itemId") final Long itemId,
                                                 @Valid @RequestBody ItemStatusUpdatePayload payload) {
        return new ResponseEntity<>(itemService.updateItemStatus(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @PutMapping("{itemId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<ItemMapper> updateItem(@Authentication final CurrentUser user,
                                           @PathVariable("itemId") final Long itemId,
                                           @Valid @RequestBody ItemUpdatePayload payload) {
        return new ResponseEntity<>(itemService.updateItem(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @DeleteMapping("{itemId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public void deleteItem(@Authentication final CurrentUser user,
                           @PathVariable("itemId") final Long itemId) {
        itemService.deleteItem(user.getId(), itemId);
    }

    @PostMapping("{itemId}/comment")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Long> createItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("itemId") final Long itemId,
                                                  @Valid @RequestBody final ItemCommentCreatePayload payload) {
        return new ResponseEntity<>(itemCommentService.createItemComment(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @GetMapping("{itemId}/comments/{page}/{size}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<List<ItemCommentMapper>> findCommentsByItemId(@PathVariable("itemId") final Long itemId,
                                                                        @PathVariable("page") final int page,
                                                                        @PathVariable("size") final int size) {
        return ResponseEntity.ok(itemCommentService.findCommentsByItemId(itemId, page, size));
    }

    @PutMapping("comment/{commentId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Void> updateItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("commentId") final Long commentId,
                                                  @Valid @RequestBody final ItemCommentUpdatePayload payload) {
        itemCommentService.updateItemComment(user.getId(), commentId, payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("comment/{commentId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Void> updateItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("commentId") final Long commentId) {
        itemCommentService.deleteComment(user.getId(), commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{itemId}/like")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Void> likeItem(@Authentication final CurrentUser user,
                                         @PathVariable("itemId") final Long itemId) {
        itemService.likeItem(user.getId(), itemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{itemId}/like")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Void> cancelLikeItem(@Authentication final CurrentUser user,
                                               @PathVariable("itemId") final Long itemId) {
        itemService.cancelLikeItem(user.getId(), itemId);
        return ResponseEntity.ok().build();
    }
}
