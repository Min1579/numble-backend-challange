package me.min.karrotmarket.user.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@RequiredArgsConstructor
public class UserLoginPayload {
    @Email
    final String email;
    final String password;
}
