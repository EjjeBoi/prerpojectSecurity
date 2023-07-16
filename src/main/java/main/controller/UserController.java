package main.controller;

import lombok.RequiredArgsConstructor;
//import main.dto.UserPostDto;
import main.models.Role;
import main.models.User;
import main.service.AuthenticationService;
import main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    private final RegisterRequest registerRequest;
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

//    @PreAuthorize("hasRole('USER')")
    @PostMapping("grant-admin-role/{id}")
    public void grantAdminRole(@PathVariable("id") Long Id) {
        User user = userService.getById(Id);
        user.setRole(Role.ADMIN);
        userService.updateUser(user);
    }
}
