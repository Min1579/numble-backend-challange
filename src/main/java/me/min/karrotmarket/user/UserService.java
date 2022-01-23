package me.min.karrotmarket.user;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.security.jwt.TokenProvider;
import me.min.karrotmarket.shared.exceoption.ConflictException;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.mapper.LoginResponseMapper;
import me.min.karrotmarket.user.model.User;
import me.min.karrotmarket.user.payload.UserAuthPayload;
import me.min.karrotmarket.user.payload.UserCreatePayload;
import me.min.karrotmarket.user.payload.UserUpdatePayload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional()
    public Long createUser(final UserCreatePayload payload) {
        validateUserCreatePayload(payload);
        payload.encodePassword(encoder.encode(payload.getPassword()));
        return userRepository.save(User.of(payload)).getId();
    }

    private void validateUserCreatePayload(final UserCreatePayload payload) {
        if (!findUserByEmail(payload.getEmail()).equals(Optional.empty())) {
            throw new ConflictException("User");
        }
    }

    @Transactional(readOnly = true)
    public LoginResponseMapper login(final UserAuthPayload payload) {
        validateUser(payload);
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                payload.getEmail(),
                payload.getPassword()
        );
        final Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.createToken(authentication);
        return LoginResponseMapper.of(token);
    }

    @Transactional
    public Long updateUser(final Long userId, final UserUpdatePayload payload) {
        final User user = findUserById(userId);
        return user.update(payload).getId();
    }

    private <T extends UserAuthPayload> void validateUser(final T payload) {
        final User user = this.findUserByEmail(payload.getEmail())
                .orElseThrow(() -> new NotFoundException("User"));
        if (!encoder.matches(payload.getPassword(), user.getPassword())) {
            throw new ConflictException("Password Not Correct");
        }
    }

    @Transactional(readOnly = true)
    public User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User"));
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }
}
