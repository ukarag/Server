package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    User me(@RequestHeader("Access-Token") String token) {
        return service.getUserByToken(token);
    }

    @GetMapping("/users/{userId}")
    ResponseEntity<User> one(@PathVariable("userId") long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getUser(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @PostMapping("/login")
        //mit token identifizieren
    User login(@RequestBody User user) {

        return this.service.login(user);
    }

    @PostMapping("/logout/{userId}")
    User logout(@PathVariable("userId") long id) {
        return service.logoutUser(id);
    }


    @PostMapping("/users")
    ResponseEntity <User> createUser(@RequestBody User newUser) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(newUser));
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username already taken", ex
            );
        }
    }

    @CrossOrigin
    @PutMapping("/users/{userId}")
    ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable("userId") Long userId) {
        User dbUser = this.service.getUser(userId);
        if (dbUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(service.replaceUser(userId, newUser));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}