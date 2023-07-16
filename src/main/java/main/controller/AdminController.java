package main.controller;

import main.models.User;
import main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/admin-api/")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("all")
    public List<User> getAllUsers() {
        logger.info("Admin requested all users.");
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-by-username/{userName}")
    public User getByUserName(@PathVariable("userName") String userName) {
        return userService.findByUserName(userName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-by-id/{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete")
    public void deleteUsers() {
        userService.deleteAll();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete_user_account/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
