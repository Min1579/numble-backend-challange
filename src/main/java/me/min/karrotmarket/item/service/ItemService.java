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
import org.springframework.data.domain.Sort;
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
    public List<ItemMapper> findAllItemByCategory(final int page,
                                                  final int size,
                                                  final Category category) {
        final PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return itemRepository.findAllByCategory(category, pageRequest)
                .stream()
                .map(ItemMapper::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemMapper findItemByRequestItemId(final Long itemId) {
        return ItemMapper.of(findItemById(itemId));
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
