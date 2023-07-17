package main.service;

import lombok.RequiredArgsConstructor;
import main.models.Role;
import main.models.User;
import main.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;

//    public List<User> getAllUsers() {
//        return repository.findAll();
//    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteUserById(Integer id) {
        User user = repository.getById(id);
        repository.delete(user);
    }

    public User findByUserName(String userName) {
        return repository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
    }

    public User getById(Long id) {
        return repository.getById(id);
    }

    public User updateUser(User user) {
        return repository.save(user);
    }


    public List<User> getAllUsers() {
        return repository.findAll();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        logger.info("Роль пользователя: {}", role);

        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
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

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.info("Загрузка пользователя по имени: {}", username);
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        logger.info("Информация о пользователе получена: {}", user);
//        logger.info("Роль пользователя: {}", user.getRole());
//
//        Collection<? extends GrantedAuthority> authorities = getAuthorities(user.getRole());
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                authorities
//        );
//    }

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
