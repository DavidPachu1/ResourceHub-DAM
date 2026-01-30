package com.example.ResourcesHub.repository;

import com.example.ResourcesHub.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Buscar todas las reservas de un usuario concreto
    List<Reserva> findByUsuarioId(Long usuarioId);

    // Buscar todas las reservas de un recurso concreto
    List<Reserva> findByRecursoId(Long recursoId);

    // Busca reservas de UN recurso concreto
    // Que NO estén canceladas ni rechazadas (porque esas no molestan)
    // Y que se solapen en el tiempo
    @Query("SELECT r FROM Reserva r WHERE r.recurso.id = :recursoId " +
            "AND r.estado NOT IN ('CANCELADA', 'RECHAZADA') " +
            "AND ((r.fechaInicio < :fin) AND (r.fechaFin > :inicio))")
    List<Reserva> findReservasEnConflicto(@Param("recursoId") Long recursoId,
                                          @Param("inicio") LocalDateTime inicio,
                                          @Param("fin") LocalDateTime fin);

}