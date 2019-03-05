package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserCheck;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/users")
    UserCheck createUser(@RequestBody User newUser) {

        try {
            return new UserCheck(this.service.createUser(newUser));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken!");
        }
    }

    @GetMapping("/users/{id}")
    UserCheck getUser(@PathVariable("id") long id){
        var user = this.service.getUserById(id);

        if (user != null){
            return new UserCheck(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/users/{id}")
    ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable("id") long id){
        var updatedUser = this.service.getUserById(id);
        if (updatedUser != null){
            try {
                this.service.updateUser(id, user);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

}
