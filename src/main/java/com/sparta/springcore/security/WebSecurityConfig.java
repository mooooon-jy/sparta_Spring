package com.sparta.springcore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 (Lambda 방식)
        http.csrf((csrf) -> csrf.disable());

        // H2 Console 등 사용을 위한 Frame Options 설정
        http.headers((headers) -> headers
                .frameOptions((frameOptions) -> frameOptions.disable())
        );

        // 요청 권한 설정
        http.authorizeHttpRequests((authorize) -> authorize
                .anyRequest().authenticated()
        );

        // 로그인 설정
        http.formLogin((form) -> form
                .defaultSuccessUrl("/")
                .permitAll()
        );

        // 로그아웃 설정
        http.logout((logout) -> logout
                .permitAll()
        );

        return http.build();
    }
}
