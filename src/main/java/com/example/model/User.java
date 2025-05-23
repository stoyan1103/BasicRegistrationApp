package com.example.model;

import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String email;
    private String password; // В базата данни ще запазвам само хеширана парола!!!

    public User() {}

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.password = hashPassword(password);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }
}