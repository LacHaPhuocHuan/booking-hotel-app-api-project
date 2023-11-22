package com.laptrinhweb.config;

import com.laptrinhweb.security.JwtAuthenticationFilter;
import com.laptrinhweb.security.Permission;
import com.laptrinhweb.security.Role;
import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter filter;
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests( auth-> {
                    auth.requestMatchers("/api/v1/auth/**","/api/v1/auth" ).permitAll();
                    auth.requestMatchers("/user").hasAnyRole(Role.USER.name(), Role.ADMIN.name());
                    auth.requestMatchers("/admin").hasAnyAuthority(Permission.admin_change.name());
                    auth.anyRequest().authenticated();
                });
        http.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider)
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
