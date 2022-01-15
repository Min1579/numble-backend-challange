package me.min.karrotmarket.item;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.mapper.ItemCommentMapper;
import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.ItemComment;
import me.min.karrotmarket.item.model.ItemImage;
import me.min.karrotmarket.item.payload.ItemCommentCreatePayload;
import me.min.karrotmarket.item.payload.ItemCommentUpdatePayload;
import me.min.karrotmarket.item.payload.ItemCreatePayload;
import me.min.karrotmarket.shared.exceoption.ForbiddenException;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.UserService;
import me.min.karrotmarket.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemImageRepository itemImageRepository;
    private final ItemCommentRepository itemCommentRepository;

    @Transactional
    public Long createItem(final Long userId, final ItemCreatePayload payload) {
        final User user = userService.findUserById(userId);
        final Item item = itemRepository.save(Item.of(payload, user));

        if (payload.getImages().size() > 0) {
            final List<ItemImage> images = payload.getImages().stream()
                    .map(image -> ItemImage.of(image, item))
                    .collect(Collectors.toList());
            itemImageRepository.saveAll(images);
        }

        return item.getId();
    }

    @Transactional
    public Long createItemComment(final Long userId, final Long itemId, final ItemCommentCreatePayload payload) {
        final User user = userService.findUserById(userId);
        final Item item = findItemById(itemId);
        return itemCommentRepository.save(ItemComment.of(user, item, payload)).getId();
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

    private Item findItemById(final Long itemId) {
        return this.itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item"));
    }

    @Transactional(readOnly = true)
    public List<ItemCommentMapper> findCommentsByItemId(final Long itemId, final int page, final int size) {
        final Item item = findItemById(itemId);
        final PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        final List<ItemComment> comments =
                itemCommentRepository.findAllByItemId(item, pageRequest);
        return comments.stream()
                .map(ItemCommentMapper::of)
                .collect(Collectors.toList());
    }
}
