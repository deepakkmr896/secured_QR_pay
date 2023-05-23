package com.secured.qrpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    // todo - check why with authentication it is not working
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((req) -> req
                        /*.antMatchers("/h2-console/**")
                        .permitAll()*/
                        .anyRequest()
                        .permitAll())
                .csrf().disable()
                .headers().frameOptions().sameOrigin();
        return http.build();
    }
}
