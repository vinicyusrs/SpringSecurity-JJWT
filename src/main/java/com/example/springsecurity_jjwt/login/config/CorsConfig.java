package com.example.springsecurity_jjwt.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig{

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
       
        config.addAllowedOrigin("*"); // 모든 ip에 응답 허용
        config.addAllowedHeader("*"); // 모든 헤더에 응답 허용
        config.addAllowedMethod("*"); // 모든 post,get,put delete,patch 요청 허용

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
