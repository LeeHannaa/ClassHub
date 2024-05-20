package com.example.classhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/auth/login", "/css/**", "/js/**", "../bootstrap/**").permitAll()
//                                .anyRequest().authenticated() // 나머지 요청은 인증 필요
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/auth/login") // 커스텀 로그인 페이지
//                                .defaultSuccessUrl("/lecture-room", true) // 로그인 성공 후 리디렉션
//                                .permitAll()
//                )
//                .logout(logout ->
//                        logout
//                                .logoutUrl("/auth/logout")
//                                .logoutSuccessUrl("/auth/login")
//                                .permitAll()
//                );

        return http.build();
    }
}
