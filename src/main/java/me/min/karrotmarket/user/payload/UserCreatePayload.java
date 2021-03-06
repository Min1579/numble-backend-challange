package me.min.karrotmarket.user.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCreatePayload extends UserAuthPayload {
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String nickname;

    public void encodePassword(final String password) {
        super.password = password;
    }
}
