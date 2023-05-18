package com.example.tp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Admin {
    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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
