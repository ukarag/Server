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
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public Boolean existsUserById(long id){
        return this.userRepository.existsUserById(id);
    }

    public Boolean existsUserByUsername(String username){
        return this.userRepository.existsUserByUsername(username);
    }

    public User getUserById(long id){
        return this.userRepository.findById(id);
    }

    public User getUserByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    public Iterable<User> getUsers(){
        return this.userRepository.findAll();
    }

    public void updateUser(long userId, User user){
        User updatedUser = this.userRepository.findById(userId);
        updatedUser.setUsername(user.getUsername());
        updatedUser.setBday(user.getBday());
        this.userRepository.save(updatedUser);
    }
}
