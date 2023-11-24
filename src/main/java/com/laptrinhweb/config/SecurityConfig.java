package com.laptrinhweb.config;

import com.laptrinhweb.security.JwtAuthenticationFilter;
import com.laptrinhweb.security.Permission;
import com.laptrinhweb.security.Role;
import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)

public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter filter;
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests( auth-> {
                    auth.requestMatchers("/swagger-ui/**","/swagger-ui/","/swagger-ui","/swagger-resources/**" ,
                            "/swagger-resources",
                            "/swagger-resources/**",
                            "/configuration/ui",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**",
                            // -- Swagger UI v3 (OpenAPI)
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/api/v1/hotel/**",
                            "/api/v1/hotel"
                    ).permitAll();
                    auth.requestMatchers("/api/v1/auth/**","/api/v1/auth" ).permitAll();
                    auth.requestMatchers("/api/v1/auth/change-password").authenticated();
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
