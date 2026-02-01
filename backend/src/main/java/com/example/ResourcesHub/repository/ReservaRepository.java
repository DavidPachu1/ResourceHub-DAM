package com.example.ResourcesHub.repository;

import com.example.ResourcesHub.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio de Spring Data JPA para la entidad {@link Reserva}.
 * Proporciona métodos para operaciones CRUD y consultas personalizadas para la gestión de reservas.
 *
 * @see Reserva
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Busca todas las reservas asociadas a un identificador de usuario específico.
     *
     * @param usuarioId El ID del usuario cuyas reservas se quieren obtener.
     * @return Una lista de {@link Reserva}s pertenecientes al usuario.
     */
    List<Reserva> findByUsuarioId(Long usuarioId);

    /**
     * Busca todas las reservas asociadas a un identificador de recurso específico.
     *
     * @param recursoId El ID del recurso cuyas reservas se quieren obtener.
     * @return Una lista de {@link Reserva}s asociadas al recurso.
     */
    List<Reserva> findByRecursoId(Long recursoId);

    /**
     * Busca reservas existentes para un recurso específico que entren en conflicto con un nuevo intervalo de tiempo.
     * Una reserva se considera en conflicto si su período se solapa con el intervalo [inicio, fin)
     * y su estado no es CANCELADA ni RECHAZADA.
     * <p>
     * La lógica de solapamiento se implementa con la condición {@code (r.fechaInicio < :fin) AND (r.fechaFin > :inicio)},
     * que detecta cualquier superposición parcial o total entre dos intervalos de tiempo.
     *
     * @param recursoId El ID del recurso a verificar.
     * @param inicio La fecha y hora de inicio del nuevo intervalo de reserva.
     * @param fin La fecha y hora de fin del nuevo intervalo de reserva.
     * @return Una lista de {@link Reserva}s que entran en conflicto con el intervalo especificado.
     *         Si la lista está vacía, no hay conflictos.
     */
    @Query("SELECT r FROM Reserva r WHERE r.recurso.id = :recursoId " +
            "AND r.estado NOT IN (com.example.ResourcesHub.model.enums.EstadoReserva.CANCELADA, com.example.ResourcesHub.model.enums.EstadoReserva.RECHAZADA) " +
            "AND ((r.fechaInicio < :fin) AND (r.fechaFin > :inicio))")
    List<Reserva> findReservasEnConflicto(@Param("recursoId") Long recursoId,
                                          @Param("inicio") LocalDateTime inicio,
                                          @Param("fin") LocalDateTime fin);

}