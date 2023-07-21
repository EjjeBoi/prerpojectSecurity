package main.repository;

import main.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtServiceTests {

    private JwtService jwtService;
    private UserDetails userDetails;
    private String secretKey = "32B5009FB35734D4DFDE016E792B8ACDB1743D32F296E743046AD81E33340531";

    @BeforeEach
    public void setup() {
        jwtService = new JwtService();
        jwtService.setSecretKey(secretKey); // Установка SECRET_KEY

        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        userDetails = new User("testuser", "testpassword", authorities);
    }

    @Test
    public void testGenerateToken_ValidUserDetails() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }
}