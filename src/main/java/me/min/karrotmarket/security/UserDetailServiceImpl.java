package me.min.karrotmarket.security;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.shared.exceoption.NotFoundException;
import me.min.karrotmarket.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return CurrentUser.create(userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User")));
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(final Long userId) throws UsernameNotFoundException {
        return CurrentUser.create(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User")));
    }
}
