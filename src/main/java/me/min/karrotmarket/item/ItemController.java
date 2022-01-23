package me.min.karrotmarket.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.mapper.ItemCommentMapper;
import me.min.karrotmarket.item.mapper.ItemDetailMapper;
import me.min.karrotmarket.item.mapper.ItemMapper;
import me.min.karrotmarket.item.model.Category;
import me.min.karrotmarket.item.model.ItemStatus;
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
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 상세")
    public ResponseEntity<ItemDetailMapper> findItemById(@Authentication final CurrentUser user,
                                                         @PathVariable("itemId") final Long itemId) {
        return ResponseEntity.ok(itemService.findItemByRequestItemId(user.getId(), itemId));
    }

    @GetMapping("category/{category}/{page}/{size}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "카테고리별 아이템 리스트")
    public ResponseEntity<List<ItemMapper>> findAllItemByCategory(@PathVariable("page") final int page,
                                                                  @PathVariable("size") final int size,
                                                                  @PathVariable("category") Category category) {
        return ResponseEntity.ok(itemService.findItemsByCategory(page, size, category));
    }

    @GetMapping("user/{userId}/{page}/{size}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "유저 아이템 리스트")
    public ResponseEntity<List<ItemMapper>> findItemsByUser(@PathVariable("page") final int page,
                                                            @PathVariable("size") final int size,
                                                            @PathVariable("userId") final Long userId) {
        return ResponseEntity.ok(itemService.findAllItemByUser(page, size, userId));
    }


    @GetMapping("me/{page}/{size}/{status}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 상태에 따른 조회")
    public ResponseEntity<List<ItemMapper>> findAllMyItems(@Authentication final CurrentUser user,
                                                           @PathVariable("page") final int page,
                                                           @PathVariable("size") final int size,
                                                           @PathVariable("status") final ItemStatus status) {
        return ResponseEntity.ok(itemService.findAllMyItems(user.getId(), page, size, status));
    }

    @GetMapping("{page}/{size}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 최신순")
    public ResponseEntity<List<ItemMapper>> findAllItems(
            @PathVariable("page") final int page,
            @PathVariable("size") final int size) {
        return ResponseEntity.ok(itemService.findAllItems(page, size));
    }

    @GetMapping("me/{page}/{size}/liked")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "좋아요한 아이템 리스트")
    public ResponseEntity<List<ItemMapper>> findAllMyLikedItem(@Authentication final CurrentUser user,
                                                               @PathVariable("page") final int page,
                                                               @PathVariable("size") final int size) {
        return ResponseEntity.ok(itemService.findAllMyLikedItem(user.getId(), page, size));
    }

    @PostMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 생성")
    public ResponseEntity<Long> createItem(@Authentication final CurrentUser user,
                                           @Valid @RequestBody ItemCreatePayload payload) {
        return new ResponseEntity<>(itemService.createItem(user.getId(), payload), HttpStatus.CREATED);
    }

    @PatchMapping("{itemId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 상태 업데이트")
    public ResponseEntity<ItemMapper> updateItemStatus(@Authentication final CurrentUser user,
                                                       @PathVariable("itemId") final Long itemId,
                                                       @Valid @RequestBody ItemStatusUpdatePayload payload) {
        return new ResponseEntity<>(itemService.updateItemStatus(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @PutMapping("{itemId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 수정")
    public ResponseEntity<ItemMapper> updateItem(@Authentication final CurrentUser user,
                                                 @PathVariable("itemId") final Long itemId,
                                                 @Valid @RequestBody ItemUpdatePayload payload) {
        return new ResponseEntity<>(itemService.updateItem(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @DeleteMapping("{itemId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 삭제")
    public void deleteItem(@Authentication final CurrentUser user,
                           @PathVariable("itemId") final Long itemId) {
        itemService.deleteItem(user.getId(), itemId);
    }

    @PostMapping("{itemId}/comment")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "댓글 작성")
    public ResponseEntity<Long> createComment(@Authentication final CurrentUser user,
                                              @PathVariable("itemId") final Long itemId,
                                              @Valid @RequestBody final ItemCommentCreatePayload payload) {
        return new ResponseEntity<>(itemCommentService.createComment(user.getId(), itemId, payload), HttpStatus.CREATED);
    }

    @PostMapping("comment/{commentId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "대댓글 작성")
    public ResponseEntity<Long> createRecomment(@Authentication final CurrentUser user,
                                                @PathVariable("commentId") final Long commentId,
                                                @Valid @RequestBody final ItemCommentCreatePayload payload) {
        return new ResponseEntity<>(itemCommentService.createRecomment(user.getId(), commentId, payload), HttpStatus.CREATED);

    }

    @GetMapping("{itemId}/comments/{page}/{size}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "댓글 목록")
    public ResponseEntity<List<ItemCommentMapper>> findCommentsByItemId(@PathVariable("itemId") final Long itemId,
                                                                        @PathVariable("page") final int page,
                                                                        @PathVariable("size") final int size) {
        return ResponseEntity.ok(itemCommentService.findCommentsByItemId(itemId, page, size));
    }

    @PutMapping("comment/{commentId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "댓글 수정")
    public ResponseEntity<Void> updateItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("commentId") final Long commentId,
                                                  @Valid @RequestBody final ItemCommentUpdatePayload payload) {
        itemCommentService.updateItemComment(user.getId(), commentId, payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("comment/{commentId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "댓글 삭제")
    public ResponseEntity<Void> updateItemComment(@Authentication final CurrentUser user,
                                                  @PathVariable("commentId") final Long commentId) {
        itemCommentService.deleteComment(user.getId(), commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{itemId}/like")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 좋아요")
    public ResponseEntity<Void> likeItem(@Authentication final CurrentUser user,
                                         @PathVariable("itemId") final Long itemId) {
        itemService.likeItem(user.getId(), itemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{itemId}/like")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "아이템 좋아요 취소")
    public ResponseEntity<Void> cancelLikeItem(@Authentication final CurrentUser user,
                                               @PathVariable("itemId") final Long itemId) {
        itemService.cancelLikeItem(user.getId(), itemId);
        return ResponseEntity.ok().build();
    }
}
