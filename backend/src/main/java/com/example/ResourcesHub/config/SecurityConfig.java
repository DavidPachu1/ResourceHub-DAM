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
import org.springframework.security.config.Customizer;

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
     * <li>Se vincula con la configuración de CORS para permitir peticiones externas.</li>
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
                // 1. 👇 ACTIVAMOS EL CORS (Vinculado a tu CorsConfig.java)
                .cors(Customizer.withDefaults()) 

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 1. Permitir acceso público a endpoints de autenticación
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2. 👇 ¡NUEVO! Permitir acceso a la documentación (Swagger/OpenAPI)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 3. El resto requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}