package com.example.ResourcesHub.runner;

import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.repositoy.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    // Inyección de dependencias: Spring nos da el repositorio automáticamente
    public DataSeeder(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Verificar si ya hay datos (para no duplicar al reiniciar)
        if (usuarioRepository.count() == 0) {

            // 2. Crear un usuario en memoria (Java)
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre("Tester");
            nuevoUsuario.setEmail("tester@example.com");
            nuevoUsuario.setPassword("123456");

            // 3. Guardarlo en la Base de Datos (SQL automático)
            usuarioRepository.save(nuevoUsuario);

            System.out.println("✅ ¡Usuario de prueba guardado con éxito!");
        } else {
            System.out.println("ℹ️ Ya existen usuarios en la base de datos.");
        }
    }
}