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

import static org.springframework.security.config.Customizer.withDefaults;

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
                .successHandler((request, response, authentication) -> {
                  response.setContentType("application/json");
                  response.getWriter().write("{\"redirectUrl\": \"/\"}");
                })
                .permitAll()
    );

    http.csrf(auth -> auth.disable());

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {  // 보안 필터를 적용하지 않을 요청 설정
    return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

}
