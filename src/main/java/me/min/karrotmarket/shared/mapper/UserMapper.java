package me.min.karrotmarket.shared.mapper;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.user.model.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMapper {
    private Long id;
    private String image;
    private String nickname;

    @Builder
    public UserMapper(Long id, String image, String nickname) {
        this.id = id;
        this.image = image;
        this.nickname = nickname;
    }

    public static UserMapper of(final User user) {
        return UserMapper.builder()
                .id(user.getId())
                .image(user.getImage())
                .nickname(user.getNickname())
                .build();
    }
}
