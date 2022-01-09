package me.min.karrotmarket.user.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponseMapper {
    private static final String type = "Bearer";
    private final String token;

    public static LoginResponseMapper of(final String token) {
        return new LoginResponseMapper(token);
    }
}
