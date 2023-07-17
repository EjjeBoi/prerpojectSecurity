package main.controller;

import lombok.RequiredArgsConstructor;
import main.config.JwtService;
import main.models.Role;
import main.models.User;
import main.service.AuthenticationService;
import main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("grant-admin-role/{id}")
    public ResponseEntity<String> grantAdminRole(@PathVariable("id") Long Id) {
        User user = userService.getById(Id);
        user.setRole(Role.ADMIN);
        userService.updateUser(user);
        JwtService jwtService = new JwtService();
        String updatedToken = jwtService.generateToken(user);
        return ResponseEntity.ok(updatedToken); //Обновленный токен с ролью ADMIN
    }
}