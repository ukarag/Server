package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    @GetMapping("/users/me")
    User me(@RequestHeader("Access-Token") String token){
        return service.getUserByToken(token);
    }
    @GetMapping("/users/{userId}")
        //System.out.println("User mit dieser ID wird mit GetMapping gesucht");
    User one(
            @PathVariable("userId") Long id) {
        return service.getUser(id);
    }

    @PostMapping("/login")
        //mit token identifizieren
    User login(@RequestBody User user) {
        return this.service.login(user);
    }

    @PostMapping("/logout")
    User logout(@RequestBody User user){
        System.out.println("Logging out");
        return this.service.logout(user);
    }

    @PostMapping("/users")
        //System.out.println("User werden mit PostMapping gesucht");
    User createUser(@RequestBody User newUser) {
        return this.service.createUser(newUser);
    }

    @PutMapping("/users/{userId}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long userId) {
        if (service.getUser(userId).getUsername() == newUser.getUsername()) {}
        else {
            service.getUser(userId).setUsername(newUser.getUsername());
        }
        service.getUser(userId).setBirthday(newUser.getBirthday());
        return service.getUser(userId);
    }
}