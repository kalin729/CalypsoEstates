package com.kalinkrumov.calypso_estates.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

    @NotBlank
    @Size(min = 3, max = 30)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 30)
    private String lastName;

    @NotBlank
    @Email
    @Size(min = 3, max = 30)
    private String username;

//    @NotBlank
//    @Email
//    @Size(min = 3, max = 30)
//    private String email;

    @NotBlank
    @Size(min = 3, max = 30)
    private String password;

    @NotBlank
    @Size(min = 3, max = 30)
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public UserRegisterDTO setEmail(String email) {
//        this.email = email;
//        return this;
//    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
