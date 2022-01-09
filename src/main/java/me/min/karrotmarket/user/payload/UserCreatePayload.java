package me.min.karrotmarket.user.payload;

import lombok.Data;
import javax.validation.constraints.Email;

@Data
public class UserCreatePayload {
    @Email
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickname;

    public UserCreatePayload(@Email String email, String password, String name, String phoneNumber, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public void encodePassword(final String password) {
        this.password = password;
    }
}
