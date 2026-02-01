package com.example.ResourcesHub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase maestra de configuración de seguridad de Spring.
 * <p>
 * Define la cadena de filtros de seguridad (SecurityFilterChain), las reglas de autorización HTTP
 * (quién puede entrar a dónde) y la gestión de sesiones.
 * <p>
 * Se utiliza {@link EnableMethodSecurity} para permitir anotaciones como @PreAuthorize en los controladores.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configura la cadena de filtros de seguridad (SecurityFilterChain).
     * <p>
     * Aquí se definen las reglas críticas:
     * <ul>
     * <li>Se deshabilita CSRF (no necesario para APIs REST stateless).</li>
     * <li>Se permite acceso público (whitelist) a los endpoints de autenticación (/api/auth/**).</li>
     * <li>Se exige autenticación para CUALQUIER otra petición.</li>
     * <li>Se configura la gestión de sesiones como STATELESS (sin estado), ya que usamos JWT.</li>
     * <li>Se añade nuestro {@link JwtAuthenticationFilter} antes del filtro estándar de usuario/contraseña.</li>
     * </ul>
     *
     * @param http El objeto constructor de la seguridad HTTP.
     * @return La cadena de filtros construida y lista para proteger la app.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Whitelist pública
                        .anyRequest().authenticated()              // El resto cerrado
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No guardar estado en servidor
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}