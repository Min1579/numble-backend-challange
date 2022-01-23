package me.min.karrotmarket.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.security.Authentication;
import me.min.karrotmarket.security.CurrentUser;
import me.min.karrotmarket.user.mapper.LoginResponseMapper;
import me.min.karrotmarket.user.payload.UserCreatePayload;
import me.min.karrotmarket.user.payload.UserAuthPayload;
import me.min.karrotmarket.user.payload.UserUpdatePayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("join")
    @Operation(summary = "회원가입")
    public ResponseEntity<Long> createUser(@Valid @RequestBody final UserCreatePayload payload) {
        return new ResponseEntity<>(userService.createUser(payload), HttpStatus.CREATED);
    }

    @PostMapping("login")
    @Operation(summary = "로그인")
    public ResponseEntity<LoginResponseMapper> login(@Valid @RequestBody final UserAuthPayload payload) {
        return ResponseEntity.ok(userService.login(payload));
    }

    @PutMapping()
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "유저 업데이트")
    public ResponseEntity<Long> updateUser(@Authentication final CurrentUser user,
                                           @Valid@RequestBody final UserUpdatePayload payload) {
        return ResponseEntity.ok(userService.updateUser(user.getId(), payload));
    }
}
