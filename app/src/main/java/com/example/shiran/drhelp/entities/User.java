package com.example.shiran.drhelp.entities;

public class User {

    private final Boolean AVAILABLE = false;
    private final String LANGUAGE = "English";

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean available;
    private String language;
    private String token;
    private String role;

    public User() {
    }

    public User(String id, String firstName, String lastName,
                String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.available = AVAILABLE;
        this.language = LANGUAGE;
    }

    public User(String id, String firstName, String lastName,
                String email, String password, Boolean available, String language, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.available = available;
        this.language = language;
        this.role = role;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", available='" + available + '\'' +
                ", language='" + language + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
