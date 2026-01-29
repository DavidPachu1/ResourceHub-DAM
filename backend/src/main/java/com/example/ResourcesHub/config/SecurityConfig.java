package com.example.ResourcesHub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration // 1. Dice a Spring: "Lee esta clase al arrancar, es de configuración"
@EnableWebSecurity // 2. Activa la seguridad web
public class SecurityConfig {

    @Bean // 3. El método devuelve un componente que Spring gestionará (el filtro de seguridad)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 4. Desactiva protección CSRF (necesario para APIs REST simples)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 5. ¡LA CLAVE! Permite (permitAll) cualquier petición (anyRequest)
                );

        return http.build();
    }
}