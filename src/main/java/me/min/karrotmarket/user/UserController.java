package me.min.karrotmarket.user;

import lombok.RequiredArgsConstructor;
import me.min.karrotmarket.user.mapper.LoginResponseMapper;
import me.min.karrotmarket.user.payload.UserCreatePayload;
import me.min.karrotmarket.user.payload.UserAuthPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@RestController
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("join")
    public ResponseEntity<Long> createUser(@Valid @RequestBody final UserCreatePayload payload) {
        return new ResponseEntity<>(userService.createUser(payload), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseMapper> login(@Valid @RequestBody final UserAuthPayload payload) {
        return ResponseEntity.ok(userService.login(payload));
    }
}
