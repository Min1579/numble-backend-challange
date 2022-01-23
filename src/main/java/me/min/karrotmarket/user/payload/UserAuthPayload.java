package me.min.karrotmarket.user.payload;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserAuthPayload {
    @Email
    @NotBlank
    protected String email;
    @NotBlank
    protected String password;
}
