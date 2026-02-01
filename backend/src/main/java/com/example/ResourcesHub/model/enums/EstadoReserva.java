package com.example.ResourcesHub.model.enums;

/**
 * Representa los posibles estados por los que puede pasar una reserva a lo largo de su ciclo de vida.
 * Cada estado define una etapa específica en el proceso de solicitud, aprobación y uso de un recurso.
 */
public enum EstadoReserva {
    /**
     * La reserva ha sido solicitada por el usuario pero aún no ha sido revisada por un administrador.
     * Es el estado inicial de toda nueva reserva.
     */
    PENDIENTE,

    /**
     * Un administrador ha validado y aprobado la solicitud de reserva. El recurso está oficialmente
     * asignado para las fechas solicitadas, pero el período de uso aún no ha comenzado.
     */
    APROBADA,

    /**
     * La reserva está en curso. El período de tiempo de la reserva ha comenzado y el usuario
     * está haciendo uso del recurso.
     */
    ACTIVA,

    /**
     * El período de la reserva ha concluido y el recurso ha sido devuelto satisfactoriamente.
     * Este es un estado final exitoso.
     */
    FINALIZADA,

    /**
     * Un administrador ha denegado la solicitud de reserva. Esto puede deberse a conflictos
     * de disponibilidad, falta de permisos u otras políticas internas.
     */
    RECHAZADA,

    /**
     * El usuario que solicitó la reserva ha decidido anularla antes de que comenzara su período de uso.
     */
    CANCELADA
}