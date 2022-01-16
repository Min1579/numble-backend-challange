package me.min.karrotmarket.user.payload;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCreatePayload extends UserAuthPayload {
    private String name;
    private String phoneNumber;
    private String nickname;

    public void encodePassword(final String password) {
        super.password = password;
    }
}
