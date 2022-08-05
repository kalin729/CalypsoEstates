package com.kalinkrumov.calypso_estates.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    //todo fix users to be uuid not hex
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public User setId(UUID id) {
        this.id = id;
        return this;
    }
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

//    @Column(nullable = false, unique = true)
//    private String email;

    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<UserRole> roles = new ArrayList<>();

//    public Long getId() {
//        return id;
//    }
//
//    public User setId(Long id) {
//        this.id = id;
//        return this;
//    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public User setEmail(String email) {
//        this.email = email;
//        return this;
//    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }
}
