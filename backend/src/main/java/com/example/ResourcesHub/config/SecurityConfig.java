package com.example.ResourcesHub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // 1. Inyecta el filtro y el proveedor automáticamente
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter; // El portero que creamos antes
    private final AuthenticationProvider authenticationProvider; // El sistema de validación

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivamos CSRF (estándar en APIs REST) para evitar ataques
                .authorizeHttpRequests(auth -> auth
                        // 2. ABRIMOS SOLO ESTAS PUERTAS (Login y Registro)
                        .requestMatchers("/api/auth/**").permitAll()
                        // 3. CERRAMOS TODAS LAS DEMÁS (Necesitan Token)
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No guardamos sesión, usamos JWT
                )
                .authenticationProvider(authenticationProvider)
                // 4. Ponemos a nuestro portero (Filtro JWT) ANTES que el portero por defecto
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}