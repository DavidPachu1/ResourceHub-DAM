package com.example.ResourcesHub.model.enums;

public enum EstadoReserva {
    PENDIENTE,   // El usuario la pidió, el admin debe aprobarla
    APROBADA,    // El admin dijo "sí", pero aún no es la fecha
    ACTIVA,      // El usuario ya tiene el recurso en la mano
    FINALIZADA,  // El recurso fue devuelto correctamente
    RECHAZADA,   // El admin dijo "no"
    CANCELADA    // El usuario se arrepintió
}
