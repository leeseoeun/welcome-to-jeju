package com.welcometojeju.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@Log4j2
public class CustomSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
            .requestMatchers("/users/create", "/users/login", "/users/logout",
                "/themes", "/themes/collaborate", "/themes/public",
                "/", "/search", "/ranking").permitAll()
            .anyRequest().authenticated()
    )
        .formLogin(formLogin ->
            formLogin
                .loginPage("/users/login")
                .permitAll()
    )
        .logout(logout ->
            logout
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
    );

    http.csrf(auth -> auth.disable());

    http.oauth2Login(oauth2Login ->
        oauth2Login
            .loginPage("/users/login")
    );

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {  // 보안 필터를 적용하지 않을 요청 설정
    return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

}
