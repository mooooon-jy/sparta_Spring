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
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // H2 Console 사용을 위한 설정
        http.headers((headers) -> headers
                .frameOptions((frameOptions) -> frameOptions.disable())
        );

        // 1. 권한 설정 (authorizeHttpRequests)
        http.authorizeHttpRequests((authorize) -> authorize
                // image 폴더를 login 없이 허용
                .requestMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .requestMatchers("/css/**").permitAll()
                // 회원 관리 처리 API (로그인 페이지 등)도 허용 필요
                .requestMatchers("/user/**").permitAll()
                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
        );

        // 2. 로그인 설정 (formLogin)
        http.formLogin((form) -> form
                .loginPage("/user/login")          // 로그인 View 페이지
                .loginProcessingUrl("/user/login") // 로그인 처리 (POST) URL
                .defaultSuccessUrl("/")            // 로그인 성공 시 이동할 페이지
                .failureUrl("/user/login/error")   // 로그인 실패 시 이동할 페이지
                .permitAll()
        );

        // 3. 로그아웃 설정 (logout)
        http.logout((logout) -> logout
                .permitAll()
        );

        return http.build();
    }
}
