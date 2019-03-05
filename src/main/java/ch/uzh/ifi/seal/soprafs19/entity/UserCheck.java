package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.util.Date;

public class UserCheck {

    public UserCheck(User user){
        this.setId(user.getId());
        this.setName(user.getName());
        this.setUsername(user.getUsername());
        this.setToken(user.getToken());
        this.setStatus(user.getStatus());
        this.setBday(user.getBday());
    }
    private Long id;

    private String name;

    private String username;

    private String token;

    private UserStatus status;

    private Date bday;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
