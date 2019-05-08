package com.example.shiran.drhelp.entities.forms;

import android.text.TextUtils;

import com.example.shiran.drhelp.entities.User;

public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String language;

    public RegistrationForm(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public User toUser(String id) {
        return new User(id, firstName, lastName,
                email,
                password,
                false,
                language);
    }


    @Override
    public String toString() {
        return "RegistrationForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


