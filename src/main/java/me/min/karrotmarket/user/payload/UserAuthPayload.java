package me.min.karrotmarket.user.payload;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserAuthPayload {
    @Email
    protected String email;
    protected String password;
}
