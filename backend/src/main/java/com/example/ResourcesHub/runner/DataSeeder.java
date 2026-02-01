package com.example.ResourcesHub.runner;

import com.example.ResourcesHub.model.enums.Rol;
import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Componente de "Bootstrapping" (Carga Inicial) de datos.
 * <p>
 * Esta clase implementa la interfaz {@link CommandLineRunner}, lo que garantiza que Spring Boot
 * ejecutará el método {@code run} una vez que el contexto de la aplicación se haya cargado completamente,
 * pero antes de que empiece a recibir tráfico HTTP.
 * <p>
 * Su responsabilidad principal es realizar el <b>Data Seeding</b> (siembra de datos), asegurando
 * que la base de datos nunca esté vacía al arrancar y garantizando la existencia de un usuario
 * administrador por defecto para el primer acceso al sistema.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Ejecuta la lógica de inicialización de usuarios predeterminados.
     * <p>
     * Aplica una estrategia <b>idempotente</b>: antes de crear cualquier registro, verifica si
     * ya existe en la base de datos (buscando por email). Esto previene errores de duplicidad
     * o reinicios fallidos si la aplicación se despliega múltiples veces.
     * <p>
     * Acciones clave:
     * <ul>
     * <li>Crea un usuario con rol {@code ADMIN} (Jefe Supremo) si no existe.</li>
     * <li>Crea un usuario con rol {@code USER} (Empleado Pepe) para pruebas de funcionalidad limitada.</li>
     * <li>Utiliza el {@link PasswordEncoder} inyectado para hashear las contraseñas antes de guardarlas,
     * manteniendo la coherencia con la política de seguridad definida en {@code SecurityConfig}.</li>
     * </ul>
     *
     * @param args Argumentos de línea de comandos pasados al arrancar la aplicación (no utilizados aquí).
     * @throws Exception Si ocurre un error crítico durante el acceso a datos que impida el arranque.
     */
    @Override
    public void run(String... args) throws Exception {

        // --- Lógica para el ADMIN ---
        if (usuarioRepository.findByEmail("admin@test.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Jefe Supremo");
            admin.setEmail("admin@test.com");
            // Es vital usar el encoder real para que el login posterior funcione
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);

            usuarioRepository.save(admin);
            System.out.println("✅ ADMIN creado: admin@test.com / admin123");
        } else {
            System.out.println("ℹ️ El usuario ADMIN ya existe (Omisión de carga inicial).");
        }

        // --- Lógica para el USER ---
        if (usuarioRepository.findByEmail("user@test.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setNombre("Empleado Pepe");
            user.setEmail("user@test.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRol(Rol.USER);

            usuarioRepository.save(user);
            System.out.println("✅ USER creado: user@test.com / user123");
        } else {
            System.out.println("ℹ️ El usuario USER ya existe (Omisión de carga inicial).");
        }
    }
}