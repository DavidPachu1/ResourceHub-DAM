package com.example.ResourcesHub.repository;

import com.example.ResourcesHub.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    // 🧠 MAGIC METHOD:
    // Spring lee esto y crea el SQL: "SELECT * FROM recursos WHERE activo = true"
    // Así nunca mostramos basura vieja.
    List<Recurso> findByActivoTrue();
}
