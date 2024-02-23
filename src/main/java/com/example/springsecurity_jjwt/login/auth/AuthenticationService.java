package com.example.springsecurity_jjwt.login.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurity_jjwt.login.config.JwtService;
import com.example.springsecurity_jjwt.login.model.Role;
import com.example.springsecurity_jjwt.login.model.User;
import com.example.springsecurity_jjwt.login.model.UserBuilder;
import com.example.springsecurity_jjwt.login.model.UserRepository;

//import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	public List<User> getAllUsers() {
	    return repository.findAll();
	}
	
	public User getUserById(Integer id) {
	    return repository.findById(id).orElse(null);
	}

	public AuthenticationResponse updateUserById(Integer id, RegisterRequest updatedUser) {

		User existingUser = repository.findById(id).orElse(null);
	    if (existingUser != null) {
	        String newEmail = updatedUser.getEmail();
	        if (newEmail != null && !newEmail.isEmpty()) {
	            // Verificar se o novo email já está em uso
	            Optional<User> userWithSameEmail = repository.findByEmail(newEmail);
	            if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
	                return AuthenticationResponse.builder()
	                        .message("O email fornecido já está sendo usado por outro usuário.")
	                        .token("não autorizado")
	                        .build();
	            }
	            existingUser.setEmail(newEmail);
	        }
          
     
            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole()); //

            String newPassword = updatedUser.getPassword();
            if (newPassword != null && !newPassword.isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(newPassword);
                existingUser.setPassword(encryptedPassword);
            }

            repository.save(existingUser);

            var jwtToken = jwtService.generateToken(existingUser);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("Usuário atualizado com sucesso")
                    .build();
        }

        return null;
    }
	
	

	public AuthenticationResponse deleteUserById(Integer id) {
	    if (repository.existsById(id)) {
	        repository.deleteById(id);
	        return AuthenticationResponse.builder()
                    .message("Usuario Deletado com sucesso")
                    .token("Deletado")
                    .build();
	    } else {
	        return AuthenticationResponse.builder()
                    .message("Erro ao deletar usuario")
                    .token("erro")
                    .build();
	    }
	}
	
	
	

	public AuthenticationResponse register(RegisterRequest request, Role role) {
	    String email = request.getEmail();
	    if (repository.findByEmail(email).isPresent()) {
	    	//System.out.println("passou por aqui");
	        // E-mail já está em uso, retorne uma resposta de erro
	        return AuthenticationResponse.builder().message("E-mail já está em uso").token("não autorizado").build();
	    }

	    // Crie um novo usuário
	    var user = new UserBuilder()
	            .firstname(request.getFirstname())
	            .lastname(request.getLastname())
	            .email(email)
	            .password(passwordEncoder.encode(request.getPassword()))
	            .role(role) // Define o papel (role) fornecido
	            .build();

	    repository.save(user);
	    var jwtToken = jwtService.generateToken(user);
	    return AuthenticationResponse.builder().token(jwtToken).message("Usuario criado com sucesso").build();
	}
	
	 public AuthenticationResponse authenticate(AuthenticationRequest request) {
		 try {
		        authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(
		                request.getEmail(),
		                request.getPassword()
		            )
		        );
		        var user = repository.findByEmail(request.getEmail())
		            .orElseThrow();

		        var jwtToken = jwtService.generateToken(user);
		        return AuthenticationResponse.builder()
		            .token(jwtToken)
		            .message("E-mail e senha okay")
		            .build();
		    } catch (AuthenticationException e) {
		        // E-mail ou senha incorretos, retorne uma resposta de erro
		        return AuthenticationResponse.builder()
		            .message("E-mail ou senha incorretos")
		            .build();
		    }
		}
}
