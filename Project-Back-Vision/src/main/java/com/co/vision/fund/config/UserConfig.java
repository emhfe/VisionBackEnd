package com.co.vision.fund.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {

    @Value("${admin.user.admin}")
    private String admin;

    @Value("${admin.user.pass}")
    private String pass;

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username(admin)
                .password(passwordEncoder.encode(pass))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
