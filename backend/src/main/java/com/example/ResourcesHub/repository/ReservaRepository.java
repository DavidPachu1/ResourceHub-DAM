package com.example.ResourcesHub.repository;

import com.example.ResourcesHub.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Magic Methods de Spring Data JPA 🪄

    // Buscar todas las reservas de un usuario concreto
    List<Reserva> findByUsuarioId(Long usuarioId);

    // Buscar todas las reservas de un recurso concreto
    List<Reserva> findByRecursoId(Long recursoId);
}