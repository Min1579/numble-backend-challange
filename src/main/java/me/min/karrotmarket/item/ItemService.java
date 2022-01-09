package me.min.karrotmarket.item;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.item.model.Item;
import me.min.karrotmarket.item.model.ItemComment;
import me.min.karrotmarket.item.model.ItemImage;
import me.min.karrotmarket.item.payload.ItemCommentCreatePayload;
import me.min.karrotmarket.item.payload.ItemCreatePayload;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.UserService;
import me.min.karrotmarket.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemImageRepository itemImageRepository;
    private final ItemCommentRepository itemCommentRepository;

    public Long createItem(final Long userId, final ItemCreatePayload payload) {
        final User user = userService.findUserById(userId);
        final Item item = itemRepository.save(Item.of(payload, user));

        final List<ItemImage> images = payload.getImages().stream()
                .map((image) -> ItemImage.of(image, item))
                .collect(Collectors.toList());
        itemImageRepository.saveAll(images);

        return item.getId();
    }

    public Long createItemComment(final Long userId, final Long itemId, final ItemCommentCreatePayload payload) {
        final User user = userService.findUserById(userId);
        final Item item = findItemById(itemId);
        return itemCommentRepository.save(ItemComment.of(user, item, payload)).getId();
    }

    private Item findItemById(final Long itemId) {
        return this.itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item"));
    }
}
