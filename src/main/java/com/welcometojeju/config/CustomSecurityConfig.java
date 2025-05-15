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
            .requestMatchers("/users/**",
                "/themes", "/themes/public", "/themes/collaborate", "/themes/get",
                "/", "/search/**",
                "http://dapi.kakao.com/**", "/sse/subscribe",
                "/favicon.ico", "/error").permitAll()
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
            .successHandler((request, response, authentication) -> {
              // 이전 페이지 URL 세션에서 가져오기
              String prevPage = (String) request.getSession().getAttribute("prevPage");
              if (prevPage != null) {
                // 리다이렉트
                response.sendRedirect(prevPage);
              } else {
                response.sendRedirect("/"); // 기본 URL로 리다이렉트
              }
            })
    );

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {  // 보안 필터를 적용하지 않을 요청 설정
    return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

}
