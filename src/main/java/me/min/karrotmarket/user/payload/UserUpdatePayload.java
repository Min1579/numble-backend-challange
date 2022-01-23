package me.min.karrotmarket.user.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserUpdatePayload {
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String nickname;
    private String image;
}
