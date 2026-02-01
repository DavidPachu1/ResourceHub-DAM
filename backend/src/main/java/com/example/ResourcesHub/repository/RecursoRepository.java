package com.example.ResourcesHub.repository;

import com.example.ResourcesHub.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de Spring Data JPA para la entidad {@link Recurso}.
 * Esta interfaz proporciona los métodos estándar para las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre los recursos, además de consultas personalizadas.
 *
 * @see Recurso
 */
public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    /**
     * Busca y devuelve una lista de todos los recursos que están marcados como activos.
     * Este método es una consulta derivada de Spring Data JPA ("query method"), que genera
     * automáticamente la consulta SQL {@code SELECT * FROM recursos WHERE activo = true}.
     * Su propósito es filtrar los recursos que han sido objeto de un borrado lógico.
     *
     * @return Una lista de {@link Recurso}s activos.
     */
    List<Recurso> findByActivoTrue();
}