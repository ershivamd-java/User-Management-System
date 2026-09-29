package com.gajendra.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gajendra.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final SecurityExceptionHandler securityExceptionHandler;


    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            SecurityExceptionHandler securityExceptionHandler) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;

        this.securityExceptionHandler = securityExceptionHandler;
    }


    // Password ko BCrypt format me encrypt karta hai
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // ==============================
    // CORS CONFIGURATION
    // ==============================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();


        // React frontend ka address
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
                
        );


        // Allowed HTTP methods
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );


        // Headers allow
        configuration.setAllowedHeaders(
                List.of("*")
        );


        // Credentials allow
        configuration.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();


        // Ye configuration sabhi APIs par apply hogi
        source.registerCorsConfiguration(
                "/**",
                configuration
        );


        return source;
    }


    // ==============================
    // SECURITY CONFIGURATION
    // ==============================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {


        http

            // CSRF disable because we are using JWT
            .csrf(csrf -> csrf.disable())


            // CORS enable
            .cors(cors -> {})


            // JWT application stateless hai
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )


            // 401 / 403 handling
            .exceptionHandling(exception -> exception

                .authenticationEntryPoint(
                    securityExceptionHandler
                )

                .accessDeniedHandler(
                    securityExceptionHandler
                )
            )


            // ==============================
            // API AUTHORIZATION
            // ==============================

            .authorizeHttpRequests(auth -> auth


                // Login/Register public
                .requestMatchers(
                    "/api/auth/register",
                    "/api/auth/login"
                ).permitAll()


                // My Profile
                // ADMIN + STAFF + STUDENT
                .requestMatchers("/api/users/me")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF",
                    "STUDENT"
                )


                // User Management
                // ADMIN only
                .requestMatchers("/api/users/**")
                .hasRole("ADMIN")


                // Enquiry
                // ADMIN + STAFF
                .requestMatchers("/api/enquiries/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // Course
                // ADMIN + STAFF
                .requestMatchers("/api/courses/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // Batch
                // ADMIN + STAFF
                .requestMatchers("/api/batches/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // Error page
                .requestMatchers("/error")
                .permitAll()


                // Baaki protected APIs
                .anyRequest()
                .authenticated()
            )


            // JWT filter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );


        return http.build();
    }
}