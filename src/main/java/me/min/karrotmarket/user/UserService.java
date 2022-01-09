package me.min.karrotmarket.user;

import me.min.karrotmarket.user.mapper.LoginResponseMapper;
import me.min.karrotmarket.user.model.User;
import me.min.karrotmarket.user.payload.UserCreatePayload;
import me.min.karrotmarket.user.payload.UserLoginPayload;

public interface UserService {
    Long createUser(final UserCreatePayload payload);
    LoginResponseMapper login(final UserLoginPayload payload);
    User findUserById(final Long userId);
    User findUserByEmail(final String email);
}
