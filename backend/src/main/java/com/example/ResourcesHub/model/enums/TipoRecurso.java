package com.example.ResourcesHub.model.enums;

/**
 * Define las categorías o tipos en los que se pueden clasificar los recursos del sistema.
 * Esta clasificación ayuda a organizar y filtrar los recursos disponibles para la reserva.
 */
public enum TipoRecurso {
    /**
     * Espacio físico destinado a la enseñanza o reuniones, como un salón de clases o sala de conferencias.
     */
    AULA,

    /**
     * Dispositivos de cómputo, como ordenadores portátiles, de escritorio o tabletas.
     */
    EQUIPO_INFORMATICO,

    /**
     * Componentes de hardware que se conectan a un equipo informático, como proyectores, ratones o teclados.
     */
    PERIFERICO,

    /**
     * Suministros consumibles o de uso general en un entorno de oficina, como pizarras blancas o rotuladores.
     */
    MATERIAL_OFICINA,

    /**
     * Material bibliográfico disponible para préstamo o consulta.
     */
    LIBRO
}