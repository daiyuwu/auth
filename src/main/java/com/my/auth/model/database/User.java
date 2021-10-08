package com.my.auth.model.database;

import com.my.auth.constant.Role;

import javax.persistence.*;

@Entity
public class User {

    public User() {}
    public User(String account
            , String password
            , String name
            , Role role) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.role = role.name();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String account;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
