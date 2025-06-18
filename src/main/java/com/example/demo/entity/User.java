package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name ="password", nullable = false, length =255 )
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
