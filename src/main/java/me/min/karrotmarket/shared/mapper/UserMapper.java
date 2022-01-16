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
    private String nickname;

    @Builder
    public UserMapper(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static UserMapper of(final User user) {
        return UserMapper.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
