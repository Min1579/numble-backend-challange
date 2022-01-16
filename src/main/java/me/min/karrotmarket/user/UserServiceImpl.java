package me.min.karrotmarket.user;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.security.jwt.TokenProvider;
import me.min.karrotmarket.shared.exceoption.DuplicatedException;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.mapper.LoginResponseMapper;
import me.min.karrotmarket.user.model.User;
import me.min.karrotmarket.user.payload.UserAuthPayload;
import me.min.karrotmarket.user.payload.UserCreatePayload;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional()
    public Long createUser(final UserCreatePayload payload) {
        this.validateUserCreatePayload(payload);
        payload.encodePassword(payload.getPassword());
        return userRepository.save(User.of(payload)).getId();
    }

    private void validateUserCreatePayload(final UserCreatePayload payload) {
        if (!this.findUserByEmail(payload.getEmail()).equals(Optional.empty())) {
            throw new DuplicatedException("User");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseMapper login(final UserAuthPayload payload) {
        this.validateUser(payload);
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                payload.getEmail(),
                payload.getPassword()
        );
        final Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.createToken(authentication);
        return LoginResponseMapper.of(token);
    }

    private void validateUser(final UserAuthPayload payload) {
        final User user = this.findUserByEmail(payload.getEmail())
                .orElseThrow(() -> new NotFoundException("User"));
        if (!encoder.matches(payload.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password Not Correct");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }
}
