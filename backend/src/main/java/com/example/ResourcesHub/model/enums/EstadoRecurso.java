package com.example.ResourcesHub.model.enums;

public enum EstadoRecurso {
    DISPONIBLE,      // Verde: Se puede reservar
    USO,             // Rojo: Lo tiene alguien
    MANTENIMIENTO,   // Naranja: Lo está arreglando el técnico
    BAJA             // Negro: A la basura
}
