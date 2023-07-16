package main.service;

import lombok.RequiredArgsConstructor;
import main.models.Role;
import main.models.User;
import main.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteUserById(Integer id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.info("Загрузка пользователя по имени: {}", username);
//
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//        logger.info("Информация о пользователе получена: {}", user);
//        logger.info("Роль пользователя: {}", user.getRole());
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRole()));
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Загрузка пользователя по имени: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        logger.info("Информация о пользователе получена: {}", user);
        logger.info("Роль пользователя: {}", user.getRole());

        Collection<? extends GrantedAuthority> authorities = getAuthorities(user.getRole());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        logger.info("Роль пользователя: {}", role);

        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        User user = userRepository.getByUserName(userName);
//        System.out.println("Loaded user: " + user); // Вывод информации о загруженном пользователе
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
//        System.out.println("Authorities: " + authorities); // Вывод списка GrantedAuthority
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                authorities
//        );
//    }
}
