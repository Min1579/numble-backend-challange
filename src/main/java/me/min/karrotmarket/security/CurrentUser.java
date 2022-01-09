package me.min.karrotmarket.security;

import lombok.Builder;
import lombok.Getter;
import me.min.karrotmarket.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class CurrentUser implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;


    @Builder
    public CurrentUser(final Long id,
                       final String username,
                       final String password,
                       final Collection<? extends GrantedAuthority> authorities,
                       final Map<String, Object> attributes) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static CurrentUser create(final User user) {
        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return CurrentUser.builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    public static CurrentUser create(final User user, final Map<String, Object> attributes) {
        return CurrentUser.create(user)
                .withAttribute(attributes);
    }

    private CurrentUser withAttribute(final Map<String, Object> attributes) {
        return CurrentUser.builder()
                .id(id)
                .username(username)
                .password(password)
                .authorities(authorities)
                .attributes(attributes)
                .build();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
