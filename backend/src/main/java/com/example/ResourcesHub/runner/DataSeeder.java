package com.example.ResourcesHub.runner;

import com.example.ResourcesHub.model.enums.Rol;
import com.example.ResourcesHub.model.enums.TipoRecurso;
import com.example.ResourcesHub.model.enums.EstadoRecurso;
import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.model.Recurso;
import com.example.ResourcesHub.repository.UsuarioRepository;
import com.example.ResourcesHub.repository.RecursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RecursoRepository recursoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // --- 1. USUARIOS (Manteniendo tu lógica original) ---
        if (usuarioRepository.findByEmail("admin@test.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Jefe Supremo");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByEmail("user@test.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setNombre("Empleado Pepe");
            user.setEmail("user@test.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRol(Rol.USER);
            usuarioRepository.save(user);
        }

        // --- 2. TUS 8 RECURSOS (Mapeados a tus Enums reales) ---
        if (recursoRepository.count() == 0) {
            
            recursoRepository.saveAll(List.of(
                crear("Sala de Juntas", TipoRecurso.AULA, "Espacio equipado para reuniones.", "/sala_juntas.jpg"),
                crear("MacBook Pro", TipoRecurso.EQUIPO_INFORMATICO, "Portátil de alta gama para desarrollo.", "/MacBook_Pro.jpg"),
                crear("Sistema de Audio", TipoRecurso.PERIFERICO, "Altavoces profesionales para eventos.", "/altavoces.jpg"),
                crear("Monitor 4K ASUS", TipoRecurso.PERIFERICO, "Monitor de alta resolución para productividad.", "/monitor_4k_Asus.jpg"),
                crear("Proyector Sony", TipoRecurso.PERIFERICO, "Proyector HD para presentaciones en sala.", "/proyector_4k_sony.jpg"),
                crear("Tablet Samsung", TipoRecurso.EQUIPO_INFORMATICO, "Dispositivo móvil para firmas y control.", "/tablet.jpeg"),
                crear("Kit Periféricos", TipoRecurso.PERIFERICO, "Teclado y ratón ergonómicos.", "/teclado_raton_vertical.jpg"),
                crear("Dron de grabación", TipoRecurso.EQUIPO_INFORMATICO, "Dron para contenido audiovisual.", "/dron.jpg")
            ));

            System.out.println("✅ ¡VAMOOOS! 8 Recursos cargados con éxito en la base de datos.");
        }
    }

    /**
     * Método auxiliar adaptado a tu entidad Recurso.java
     */
    private Recurso crear(String nombre, TipoRecurso tipo, String desc, String url) {
        Recurso r = new Recurso();
        r.setNombre(nombre);
        r.setTipo(tipo);
        r.setDescripcion(desc);
        r.setDescripcionAccesible("Imagen de " + nombre); // Rellenamos este campo obligatorio
        r.setUrlImagen(url);
        r.setEstado(EstadoRecurso.DISPONIBLE); // Usamos tu Enum real
        r.setActivo(true);
        r.setCodigoQr("QR-" + nombre.toUpperCase().replace(" ", "")); // Generamos un código único básico
        return r;
    }
}