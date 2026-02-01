package com.example.ResourcesHub.model;

import com.example.ResourcesHub.model.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad que representa la reserva de un {@link Recurso} por parte de un {@link Usuario}.
 * Contiene la información sobre el intervalo de tiempo de la reserva, su estado actual
 * y las relaciones con el usuario y el recurso implicados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    /**
     * Identificador único de la reserva, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fecha y hora exactas en las que comienza el período de la reserva.
     */
    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * Fecha y hora exactas en las que finaliza el período de la reserva.
     */
    @Column(nullable = false)
    private LocalDateTime fechaFin;

    /**
     * Marca de tiempo que registra el momento exacto en que se solicitó la reserva.
     * Se inicializa automáticamente y es útil para fines de auditoría.
     */
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    /**
     * El {@link Usuario} que realiza la reserva. Es una relación muchos a uno,
     * ya que un usuario puede tener muchas reservas.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * El {@link Recurso} que está siendo reservado. Es una relación muchos a uno,
     * ya que un recurso puede ser reservado muchas veces (en diferentes momentos).
     */
    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recurso;

    /**
     * El estado actual de la reserva dentro de su ciclo de vida,
     * definido por el enum {@link EstadoReserva}. Por defecto, es {@code PENDIENTE}.
     */
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado = EstadoReserva.PENDIENTE;

    /**
     * Constructor para crear una instancia de Reserva con los campos esenciales.
     *
     * @param usuario El usuario que realiza la reserva.
     * @param recurso El recurso a reservar.
     * @param fechaInicio La fecha y hora de inicio de la reserva.
     * @param fechaFin La fecha y hora de fin de la reserva.
     */
    public Reserva(Usuario usuario, Recurso recurso, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoReserva.PENDIENTE;
        this.fechaSolicitud = LocalDateTime.now();
    }
}