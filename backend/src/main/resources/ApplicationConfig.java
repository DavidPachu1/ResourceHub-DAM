package com.example.ResourcesHub.config;

import com.example.ResourcesHub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración central de componentes de seguridad de la aplicación.
 * <p>
 * Esta clase registra en el contenedor de Spring (IoC) los beans fundamentales
 * para el proceso de autenticación, como el codificador de contraseñas,
 * el servicio de búsqueda de usuarios y el proveedor de autenticación.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository usuarioRepository;

    /**
     * Define la estrategia para localizar usuarios en la base de datos.
     * <p>
     * Se utiliza una expresión lambda para implementar la interfaz funcional {@link UserDetailsService}.
     * Spring Security utilizará este bean durante el proceso de login para cargar los datos del usuario
     * (roles, permisos, contraseña) basándose en el email proporcionado.
     *
     * @return Una instancia de UserDetailsService vinculada al repositorio de usuarios.
     * @throws UsernameNotFoundException Si el email no existe en la base de datos.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));
    }

    /**
     * Configura el proveedor de autenticación (AuthenticationProvider).
     * <p>
     * Se utiliza {@link DaoAuthenticationProvider}, que es la implementación estándar para
     * recuperar datos de usuario de una base de datos (vía UserDetailsService) y verificar
     * la contraseña (vía PasswordEncoder).
     *
     * @return El proveedor de autenticación configurado con nuestras reglas de negocio.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Expone el gestor de autenticación (AuthenticationManager) principal de la aplicación.
     * <p>
     * Este componente actúa como orquestador del proceso de login. Recibe las credenciales
     * y delega la validación al {@link AuthenticationProvider} configurado.
     * Es necesario exponerlo como Bean para poder inyectarlo en el Servicio de Autenticación.
     *
     * @param config Configuración global de autenticación de Spring.
     * @return El AuthenticationManager listo para ser usado.
     * @throws Exception Si ocurre un error al construir el gestor.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define el algoritmo de encriptación de contraseñas.
     * <p>
     * Se utiliza BCrypt, que es el estándar actual de la industria.
     * Este encoder aplica hashing y salting automáticamente para asegurar que las contraseñas
     * nunca se guarden en texto plano.
     *
     * @return Una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}