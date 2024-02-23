package com.example.springsecurity_jjwt.login.auth;

import java.util.Objects;

import com.example.springsecurity_jjwt.login.model.Role;

//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class RegisterRequest {

	private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    

    public RegisterRequest() {
    }

    public RegisterRequest(String firstname, String lastname, String email, String password, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    
    
    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	// Getters and Setters
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&  Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, email, password, role);
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}