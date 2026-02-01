package com.example.ResourcesHub.model.enums;

/**
 * Representa la condición o estado operativo actual de un recurso.
 * Este estado es fundamental para determinar si un recurso puede ser reservado o si requiere atención.
 */
public enum EstadoRecurso {
    /**
     * El recurso está operativo y no tiene ninguna reserva activa en el momento actual,
     * por lo que está libre para ser solicitado.
     */
    DISPONIBLE,

    /**
     * El recurso está actualmente asignado a un usuario a través de una reserva activa.
     * No está disponible para nuevas reservas que se solapen con el período actual.
     */
    EN_USO,

    /**
     * El recurso ha sido retirado temporalmente de la circulación para reparaciones,
     * actualizaciones o cualquier tipo de servicio técnico. No está disponible para reservas.
     */
    MANTENIMIENTO,

    /**
     * El recurso ha sido descontinuado o dado de baja permanentemente.
     * Ya no forma parte del inventario activo y no puede ser reservado.
     */
    DE_BAJA
}