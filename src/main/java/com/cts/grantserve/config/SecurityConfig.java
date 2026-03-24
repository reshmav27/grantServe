package com.cts.grantserve.config;

import com.cts.grantserve.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Use this instead of @EnableWebMvc for Security
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/register").permitAll() // ALLOW THESE WITHOUT LOGIN
                // .requestMatchers("/User/register").hasRole("APPLICANT")
//                .requestMatchers("/auth/login", "/auth/register").permitAll()

                // 1. PROGRAM MANAGER
                .requestMatchers("/api/v1/programs/createProgram", "/api/v1/programs/update", "/api/v1/programs/manager/search").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/programs/**", "/api/v1/budgets/**").hasRole("MANAGER")

                // 2. ADMIN ONLY: Get all researchers or all documents
                .requestMatchers(HttpMethod.POST, "/api/researcher/register").permitAll()
                .requestMatchers("/api/researcher/all").hasRole("ADMIN")
                .requestMatchers("/api/documents/all").hasRole("ADMIN")

                // 3. RESEARCHER & ADMIN: Specific Researcher Access
                // Note: Logic to ensure Researcher A can't delete Researcher B
                // should be handled inside the Service layer since we aren't using @PreAuthorize.
                .requestMatchers(HttpMethod.GET, "/api/researcher/{id}").hasAnyRole("RESEARCHER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/researcher/delete/{id}").hasAnyRole("RESEARCHER", "ADMIN")

                // 4. DOCUMENTS: Upload and Delete
                .requestMatchers(HttpMethod.POST, "/api/documents/upload").hasRole("RESEARCHER")
                .requestMatchers(HttpMethod.GET, "/api/documents/{id}").hasAnyRole("RESEARCHER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/documents/delete/{id}").hasRole("RESEARCHER")

//                // 5. EXISTING MANAGER RULES
//                .requestMatchers(HttpMethod.GET, "/api/v1/programs/**", "/api/v1/budgets/**").permitAll()
//                .requestMatchers("/api/v1/programs/createProgram", "/api/v1/programs/update").hasRole("MANAGER")
//                .requestMatchers(HttpMethod.PATCH, "/api/v1/programs/**", "/api/v1/budgets/**").hasRole("MANAGER")

                .anyRequest().authenticated() // LOCK EVERYTHING ELSE
            )
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

}