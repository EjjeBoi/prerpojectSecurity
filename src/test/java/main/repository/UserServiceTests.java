package main.repository;

import main.models.User;
import main.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetPersonById() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("Username1");
        user.setEmail("user1@mail.ru");

        // when
        when(userRepository.getById(ArgumentMatchers.anyLong())).thenReturn(user);

        User retrievedUser = userService.getById(1L);

        // then
        assertThat(retrievedUser, Matchers.notNullValue());

        assertThat(retrievedUser.getId(), Matchers.equalTo(1L));
        assertThat(retrievedUser.getUsername(), Matchers.equalTo("Username1"));
        assertThat(retrievedUser.getEmail(), Matchers.equalTo("user1@mail.ru"));
    }

    @Test
    public void testFindPersonByUsername() {
        // given
        User user = new User();
        user.setUsername("Username1");
        user.setEmail("user1@mail.ru");
        user.setPassword("111");

        // when
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName("Username1"));

        // then
        assertTrue(userOptional.isPresent());
        User retrievedUser = userOptional.get();

        assertEquals("Username1", retrievedUser.getUsername());
        assertEquals("user1@mail.ru", retrievedUser.getEmail());
        assertEquals("111", retrievedUser.getPassword());
    }

}
