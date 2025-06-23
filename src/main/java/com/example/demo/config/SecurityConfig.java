package com.example.demo.config;

import com.example.demo.security.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /* BCryptPasswordEncoder =>  */

    // public을 붙이면 경고를 띄움
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /* SecurityFilterChain 객체 생성 */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) //csrf().disable()
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/user/list").hasAnyRole("ADMIN")
                                .requestMatchers("/", "/index",
                                        "/js/**", "/dist/**", "/image/**", "/upload/**",
                                        "/board/list", "/board/detail", "/user/signup", "/user/login",
                                        "/comment/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(login -> login
                        .usernameParameter("email")
                        .passwordParameter("pwd")
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/board/list")
                        .failureUrl("/user/login")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/"))
                .build();
    }

    // userDetailsService : spring에서 만든 클래스와 같은 객체
    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserService(); // security 패키지에 생성
    }

    // authenticationManager 객체 생성
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
