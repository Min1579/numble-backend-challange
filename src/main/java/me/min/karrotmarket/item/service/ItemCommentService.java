package me.min.karrotmarket.item.service;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.mapper.ItemCommentMapper;
import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.ItemComment;
import me.min.karrotmarket.item.payload.ItemCommentCreatePayload;
import me.min.karrotmarket.item.payload.ItemCommentUpdatePayload;
import me.min.karrotmarket.item.repository.ItemCommentRepository;
import me.min.karrotmarket.shared.exceoption.ForbiddenException;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.UserService;
import me.min.karrotmarket.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemCommentService {
    private final UserService userService;
    private final ItemService itemService;
    private final ItemCommentRepository itemCommentRepository;

    @Transactional
    public Long createComment(final Long userId, final Long itemId, final ItemCommentCreatePayload payload) {
        final User user = userService.findUserById(userId);
        final Item item = itemService.findItemById(itemId);
        final ItemComment comment = itemCommentRepository.save(ItemComment.of(user, item, payload));
        item.incrCommentCount();
        return comment.getId();
    }

    @Transactional
    public Long createRecomment(final Long userId,
                                final Long commentId,
                                final ItemCommentCreatePayload payload) {
        final User user = userService.findUserById(userId);
        final ItemComment parent = findItemCommentById(commentId);
        final ItemComment recomment = ItemComment.of(user, parent, payload.getComment());
        parent.addRecomment(recomment);
        return itemCommentRepository.save(recomment).getId();
    }

    @Transactional
    public void updateItemComment(final Long userId, final Long commentId, final ItemCommentUpdatePayload payload) {
        final ItemComment itemComment = findItemCommentById(commentId);
        validAccessForItemComment(userId, itemComment);
        itemComment.updateComment(payload);
    }

    @Transactional
    public void deleteComment(final Long userId, final Long commentId) {
        final ItemComment itemComment = findItemCommentById(commentId);
        validAccessForItemComment(userId, itemComment);
        itemComment.updateDeletedAt();
    }

    private void validAccessForItemComment(final Long userId, final ItemComment itemComment) {
        if (!itemComment.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }
    }

    private ItemComment findItemCommentById(final Long itemCommentId) {
        return this.itemCommentRepository.findById(itemCommentId)
                .orElseThrow(() -> new NotFoundException("ItemComment"));
    }


    @Transactional(readOnly = true)
    public List<ItemCommentMapper> findCommentsByItemId(final Long itemId, final int page, final int size) {
        final Item item = this.itemService.findItemById(itemId);
        final PageRequest pageRequest = PageRequest.of(page, size);
        final List<ItemComment> comments =
                itemCommentRepository.findItemCommentsByItemOrderByCreatedAtAsc(item, pageRequest);
        return comments.stream()
                .map(ItemCommentMapper::of)
                .collect(Collectors.toList());
    }
}
