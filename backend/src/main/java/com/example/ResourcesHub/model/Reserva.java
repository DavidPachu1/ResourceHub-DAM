package com.example.ResourcesHub.model;

import com.example.ResourcesHub.model.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 📅 FECHAS: Usamos LocalDateTime (Año, Mes, Día, Hora, Minuto)
    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    // Fecha en la que se pidió (útil para auditoría: "¿Cuándo reservaste esto?")
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    // 🔗 RELACIÓN CON USUARIO (Muchos a Uno)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Crea la columna 'usuario_id' en la tabla
    private Usuario usuario;

    // 🔗 RELACIÓN CON RECURSO (Muchos a Uno)
    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false) // Crea la columna 'recurso_id' en la tabla
    private Recurso recurso;

    // 🚦 ESTADO
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado = EstadoReserva.PENDIENTE; // Por defecto nace pendiente

    // --- CONSTRUCTORES ---
    public Reserva() {}

    public Reserva(Usuario usuario, Recurso recurso, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoReserva.PENDIENTE;
        this.fechaSolicitud = LocalDateTime.now();
    }

    // --- GETTERS Y SETTERS ---
    // (Genera los Getters y Setters con IntelliJ: Clic derecho -> Generate...)
}