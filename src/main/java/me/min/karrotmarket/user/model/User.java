package me.min.karrotmarket.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.min.karrotmarket.shared.BaseEntity;
import me.min.karrotmarket.user.payload.UserCreatePayload;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String image;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public User(Long id, String email, String password, String name, String image, String phoneNumber, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public static User of(UserCreatePayload payload) {
        return User.builder()
                .email(payload.getEmail())
                .name(payload.getName())
                .nickname(payload.getNickname())
                .password(payload.getPassword())
                .phoneNumber(payload.getPhoneNumber()).build();
    }
}
