package com.example.springsecurity_jjwt.login.config;

import java.io.IOException;

//import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
    	 
    	response.addHeader("Access-Control-Allow-Origin", "*");
    	response.addHeader("Access-Control-Allow-Headers", "*");
    	response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
    	response.addHeader("Access-Control-Max-Age", "3600");
    	
    	response.setHeader("Access-Control-Allow-Origin", "*");
    	response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    	response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
    	
    
    	
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        System.out.println("passou antes Bearer");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        System.out.println("passou no Bearer");
     // token existe mais não é valido - preciso customizar a minha mensagem de erro,  sem ter a msg padrão 403
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("passou antes do do useremail");
        jwt = authHeader.substring(7);
        System.out.println("passou antes do do meio  useremail");
        userEmail = jwtService.extractUsername(jwt);
        System.out.println("passou antes useremail");
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	System.out.println("passou no useremail");
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
            
            	System.out.println("passou no Details");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }  
        	System.out.println("passou no else");
			
			
       
     
   
        filterChain.doFilter(request, response);
}
}
