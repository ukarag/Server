package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserCheck;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import ch.uzh.ifi.seal.soprafs19.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginController {

    private final UserService userSvc;
    private final LoginService loginSvc;

    LoginController(LoginService loginSvc, UserService userSvc) {
        this.loginSvc = loginSvc;
        this.userSvc = userSvc;
    }


    @PostMapping("/login")
    UserCheck login(@RequestBody User user) {
        // check if username exists check password and username else throw exception
        if (this.userSvc.existsUserByUsername(user.getUsername())){
            // check if username and password are compatible else throw exception
            if (this.loginSvc.loginPossible(user.getUsername(), user.getPassword())){
                return new UserCheck(this.loginSvc.login(user));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your username or password is wrong");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You have to register first");
        }
    }

}
