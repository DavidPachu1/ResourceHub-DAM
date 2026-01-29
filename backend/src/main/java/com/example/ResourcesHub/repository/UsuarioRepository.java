package com.example.ResourcesHub.repository;

import com.example.ResourcesHub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repositorio para gestionar operaciones de acceso a datos de la entidad {@link Usuario}.
 *
 * Proporciona métodos CRUD heredados de {@link JpaRepository}, incluyendo operaciones
 * para guardar, buscar, actualizar y eliminar usuarios en la base de datos.
 * Spring Data JPA genera automáticamente la implementación de estos métodos.
 *
 * @author michael
 * @see Usuario
 * @see JpaRepository
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Al extender de JpaRepository, ya tienes automáticamente:
    // .save() -> Guardar
    // .findAll() -> Buscar todos
    // .findById() -> Buscar por ID
    // .delete() -> Borrar

}
