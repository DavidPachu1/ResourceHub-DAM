package com.example.ResourcesHub.repository;

import com.example.ResourcesHub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de Spring Data JPA para la entidad {@link Usuario}.
 * Esta interfaz es fundamental para la capa de persistencia, proveyendo una abstracción
 * sobre la base de datos para las operaciones relacionadas con los usuarios.
 *
 * @see Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * Este método es crucial para el proceso de autenticación, donde el email actúa como nombre de usuario.
     * La implementación es generada automáticamente por Spring Data JPA basado en la nomenclatura del método.
     *
     * @param email La dirección de correo electrónico a buscar.
     * @return Un {@link Optional} que contiene al {@link Usuario} si se encuentra, o un Optional vacío si no existe.
     */
    Optional<Usuario> findByEmail(String email);
}