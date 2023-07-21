package main.repository;

import main.config.JwtService;
import main.controller.AuthenticationRequest;
import main.controller.AuthenticationResponse;
import main.controller.RegisterRequest;
import main.models.Role;
import main.models.User;
import main.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testRegister() {

        // given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("UserFirstname");
        registerRequest.setLastname("UserLastname");
        registerRequest.setEmail("user@mail.ru");
        registerRequest.setUsername("User1");
        registerRequest.setPassword("111");

        User savedUser = User.builder()
                .id(1L)
                .firstname("UserFirstname")
                .lastname("UserLastname")
                .email("user@mail.ru")
                .username("User1")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        // when
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        // then
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    public void testAuthenticate() {

        // given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("User1");
        authenticationRequest.setPassword("111");

        User user = User.builder()
                .id(1L)
                .firstname("UserFirstname")
                .lastname("UserLastname")
                .email("user@mail.ru")
                .username("User1")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        // when
        Authentication authentication = Mockito.mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        // then
        assertEquals("jwtToken", response.getToken());
    }
}
