package com.example.springsecurity_jjwt.login.model;

public class UserBuilder {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

    // Construtor vazio
    public UserBuilder() {
    }

    // Setters para os campos

    public UserBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserBuilder lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder role(Role role) {
        this.role = role;
        return this;
    }
    // Método builder para criar uma nova instância de UserBuilder

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // Método build para criar uma instância de User
    public User build() {
        return new User(id, firstname, lastname, email, password, role);
    }
}