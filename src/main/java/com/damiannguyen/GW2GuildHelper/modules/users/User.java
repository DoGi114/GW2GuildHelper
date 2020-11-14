package com.damiannguyen.GW2GuildHelper.modules.users;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    private Long id;
    private String email;
    private String password;
    @Transient
    private String passwordConfirm;

    public User() {

    }

    public User(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
