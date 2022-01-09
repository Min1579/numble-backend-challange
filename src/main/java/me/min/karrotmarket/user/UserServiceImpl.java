package me.min.karrotmarket.user;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.security.jwt.TokenProvider;
import me.min.karrotmarket.shared.exceoption.DuplicatedException;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.mapper.LoginResponseMapper;
import me.min.karrotmarket.user.model.User;
import me.min.karrotmarket.user.payload.UserCreatePayload;
import me.min.karrotmarket.user.payload.UserLoginPayload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        payload.encodePassword(encoder.encode(payload.getPassword()));
        return userRepository.save(User.of(payload)).getId();
    }

    private void validateUserCreatePayload(final UserCreatePayload payload) {
        if (this.findUserByEmail(payload.getEmail()) != null) {
            throw new DuplicatedException("Email");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseMapper login(final UserLoginPayload payload) {
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                payload.getEmail(),
                payload.getPassword()
        );
        final Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.createToken(authentication);
        return LoginResponseMapper.of(token);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User"));
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User"));
    }
}
