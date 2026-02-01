package com.example.ResourcesHub.runner;

import com.example.ResourcesHub.model.enums.Rol;
import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // 1. Usamos Lombok para inyectar (más limpio)
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // 2. Inyectamos la herramienta de encriptar

    @Override
    public void run(String... args) throws Exception {

        // --- ADMIN ---
        if (usuarioRepository.findByEmail("admin@test.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Jefe Supremo");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);
            System.out.println("✅ ADMIN creado: admin@test.com / admin123");
        } else {
            // 👇 AÑADE ESTO
            System.out.println("ℹ️ El usuario ADMIN ya existe (No se crea de nuevo).");
        }

        // --- USER ---
        if (usuarioRepository.findByEmail("user@test.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setNombre("Empleado Pepe");
            user.setEmail("user@test.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRol(Rol.USER);
            usuarioRepository.save(user);
            System.out.println("✅ USER creado: user@test.com / user123");
        } else {
            // 👇 AÑADE ESTO
            System.out.println("ℹ️ El usuario USER ya existe (No se crea de nuevo).");
        }
    }
}