package com.example.springsecurity_jjwt.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

// import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
  
    private final AuthenticationProvider authenticationProvider;

    
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, CorsFilter corsFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
       
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                
                .authorizeHttpRequests()
                .requestMatchers("/users/authenticate")
                .permitAll()
                .requestMatchers("/dadosplanilhatech")
               .permitAll()
               .requestMatchers("dadosplanilhatech/find_by_mac")
               .permitAll()
       
               .requestMatchers("/ws")
               .permitAll()
            
                .requestMatchers("/users/**")
              .permitAll()
             //   .requestMatchers("/transmissores/**")
           //     .permitAll()
           //     .requestMatchers("/transmissores")
            //    .permitAll()
                .anyRequest()
                .authenticated()
                .and().cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
             //   .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
              //  .addFilterBefore(corsFilter, JwtAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    
    
}
