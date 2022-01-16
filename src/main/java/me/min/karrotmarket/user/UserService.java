package me.min.karrotmarket.user;

import me.min.karrotmarket.user.mapper.LoginResponseMapper;
import me.min.karrotmarket.user.model.User;
import me.min.karrotmarket.user.payload.UserAuthPayload;
import me.min.karrotmarket.user.payload.UserCreatePayload;

import java.util.Optional;

public interface UserService {
    Long createUser(final UserCreatePayload payload);
    LoginResponseMapper login(final UserAuthPayload payload);
    User findUserById(final Long userId);
    Optional<User> findUserByEmail(final String email);
}
