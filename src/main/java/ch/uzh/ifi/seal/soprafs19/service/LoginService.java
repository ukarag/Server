package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class LoginService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserService userSvc;
    private final UserRepository userRepository;


    @Autowired
    public LoginService(UserRepository userRepository, UserService userSvc) {
        this.userRepository = userRepository;
        this.userSvc = userSvc;
    }

    public Boolean loginPossible(String username, String password){
        return this.userRepository.findByUsername(username).getPassword().equals(password);
    }

    public Boolean existsUserByUsername(String username){
        return this.userRepository.existsUserByUsername(username);
    }


    public User login(User user){
        var loggedUser = this.userSvc.getUserByUsername(user.getUsername());
        loggedUser.setToken(UUID.randomUUID().toString());
        loggedUser.setStatus(UserStatus.ONLINE);
        return loggedUser;
    }
}
