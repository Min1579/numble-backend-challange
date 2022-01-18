package me.min.karrotmarket.item.service;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.mapper.ItemMapper;
import me.min.karrotmarket.item.model.*;
import me.min.karrotmarket.item.payload.*;
import me.min.karrotmarket.item.repository.ItemImageRepository;
import me.min.karrotmarket.item.repository.ItemRepository;
import me.min.karrotmarket.item.repository.LikedItemRepository;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.UserService;
import me.min.karrotmarket.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemImageRepository itemImageRepository;
    private final LikedItemRepository likedItemRepository;
    private final ItemStatus deletedStatus = ItemStatus.DELETED;
    private final List<ItemStatus> validStatus = List.of(ItemStatus.SALE, ItemStatus.RESERVATION, ItemStatus.DONE);

    @Transactional
    public ItemMapper createItem(final Long userId, final ItemCreatePayload payload) {
        final User user = userService.findUserById(userId);
        final Item item = itemRepository.save(Item.of(payload, user));
        final List<ItemImage> images = new ArrayList<>();
        for (final String image : payload.getImages()) {
            ItemImage of = ItemImage.of(image, item);
            images.add(of);
        }
        itemImageRepository.saveAll(images);
        return ItemMapper.of(item);
    }

    @Transactional
    public ItemMapper updateItemStatus(final Long userId,
                                       final Long itemId,
                                       final ItemStatusUpdatePayload payload) {
        final Item item = getItemForUpdate(userId, itemId)
                .updateItemStatus(payload.getStatus());
        return ItemMapper.of(item);
    }

    @Transactional
    public ItemMapper updateItem(final Long userId,
                                 final Long itemId,
                                 final ItemUpdatePayload payload) {
        final Item item = getItemForUpdate(userId, itemId)
                .updateItem(payload);
        if (item.getImages().size() > 0) {
            itemImageRepository.deleteAllByItem(item);
        }
        return ItemMapper.of(item);
    }

    @Transactional
    public void deleteItem(final Long userId, final Long itemId) {
        this.getItemForUpdate(userId, itemId).updateItemStatus(ItemStatus.DELETED);
    }

    @Transactional(readOnly = true)
    public List<ItemMapper> findItemsByCategory(final int page,
                                                final int size,
                                                final Category category) {
        final List<Item> items
                = itemRepository.findAllByCategoryAndStatusIsNotOrderByCreatedAtDesc(category, ItemStatus.DELETED, getPageRequest(page, size));
        return getItemMappers(items);
    }

    @Transactional(readOnly = true)
    public List<ItemMapper> findAllItemByUser(final int page,
                                              final int size,
                                              final Long userId) {
        final User user = userService.findUserById(userId);
        final List<Item> items =
                itemRepository.findAllByUserAndStatusIsNotOrderByCreatedAtDesc(user, deletedStatus, getPageRequest(page, size));
        return getItemMappers(items);
    }

    @Transactional(readOnly = true)
    public List<ItemMapper> findAllMyItems(final Long userId,
                                           final int page,
                                           final int size,
                                           final ItemStatus status) {
        final User user = userService.findUserById(userId);
        final List<Item> items
                = itemRepository.findAllByUserAndStatusOrderByCreatedAtDesc(user, status, getPageRequest(page, size));
        return getItemMappers(items);
    }

    @Transactional(readOnly = true)
    public List<ItemMapper> findAllMyLikedItem(final Long userId,
                                               final int page,
                                               final int size) {
        final User user = userService.findUserById(userId);
        final List<Item> items = itemRepository.findAllMyLikedItem(user, validStatus, getPageRequest(page, size));
        return getItemMappers(items);
    }

    @Transactional(readOnly = true)
    public ItemMapper findItemByRequestItemId(final Long itemId) {
        final Item item = findItemById(itemId);
        if (item.getStatus().equals(deletedStatus)) {
            throw new NotFoundException("User");
        }
        return ItemMapper.of(item);
    }

    private List<ItemMapper> getItemMappers(List<Item> items) {
        return items.stream()
                .map(ItemMapper::of)
                .collect(Collectors.toList());
    }

    private PageRequest getPageRequest(final int page, final int size) {
        return PageRequest.of(page, size);
    }

    private Item getItemForUpdate(final Long userId, final Long itemId) {
        final Item item = findItemById(itemId);
        if (!item.getUser().getId().equals(userId)) {
            throw new RuntimeException();
        }
        return item;
    }

    @Transactional
    public Item findItemById(final Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item"));
    }

    @Transactional
    public void likeItem(final Long userId, final Long itemId) {
        final Item item = findItemById(itemId);
        if (item.getStatus().equals(ItemStatus.DELETED)) {
            throw new NotFoundException("Item");
        }
        final User user = userService.findUserById(userId);
        if (!existLikedItem(user, item)) {
            likedItemRepository.save(LikedItem.of(user, item));
            item.incrLikeCount();
        }
    }

    @Transactional
    public void cancelLikeItem(final Long userId, final Long itemId) {
        final Item item = findItemById(itemId);
        if (item.getStatus().equals(ItemStatus.DELETED)) {
            throw new NotFoundException("Item");
        }
        final User user = userService.findUserById(userId);
        if (existLikedItem(user, item)) {
            likedItemRepository.deleteByUserAndItem(user, item);
            item.descLikeCount();
        }
    }

    private boolean existLikedItem(final User user, final Item item) {
        return likedItemRepository.existsByUserAndItem(user, item);
    }
}
