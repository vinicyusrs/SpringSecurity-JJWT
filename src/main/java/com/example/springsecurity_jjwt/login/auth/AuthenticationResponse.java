package com.example.springsecurity_jjwt.login.auth;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class AuthenticationResponse {

	private String token;
	private String message;

	public AuthenticationResponse(String token, String message) {
		super();
		this.token = token;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AuthenticationResponse() {
		super();
	}

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    public static class AuthenticationResponseBuilder {
        private String token;
        private String message;

        private AuthenticationResponseBuilder() {
        }

        public AuthenticationResponseBuilder token(String token) {
            this.token = token;
            return this;
        }
        
        public AuthenticationResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(token, message);
        }
    }
}