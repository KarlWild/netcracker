package com.netcracker.auto.web.dto;

import com.netcracker.auto.validators.PasswordMatches;
import com.netcracker.auto.validators.ValidEmail;
import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;

@PasswordMatches
public class UserDTO {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotEmpty
    @NotNull
    //@ValidEmail
    private String email;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // standard getters and setters
}
