package com.epam.grashin.reservingtask.entity;

public class User {
    private String login;
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }
}
