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
            .requestMatchers("/me/create", "/me/login", "/me/logout",
                "/themes", "/themes/public", "/themes/collaborate",
                "/", "/search/**", "/ranking", "/error").permitAll()
            .anyRequest().authenticated()
    )
        .formLogin(formLogin ->
            formLogin
                .loginPage("/me/login")
                .permitAll()
    )
        .logout(logout ->
            logout
                .logoutUrl("/me/logout")
                .logoutSuccessUrl("/")
    );

    http.csrf(auth -> auth.disable());

    http.oauth2Login(oauth2Login ->
        oauth2Login
            .loginPage("/me/login")
            .successHandler((request, response, authentication) -> {
              response.sendRedirect("/");
            })
    );

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {  // 보안 필터를 적용하지 않을 요청 설정
    return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

}
