package com.example.ResourcesHub.model;

import com.example.ResourcesHub.model.enums.EstadoRecurso;
import com.example.ResourcesHub.model.enums.TipoRecurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un recurso físico o digital que puede ser reservado en el sistema.
 * Esta clase es mapeada a la tabla 'recursos' en la base de datos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recursos")
public class Recurso {

    /**
     * Identificador único del recurso, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre descriptivo del recurso. Es un campo obligatorio.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Descripción detallada del recurso y sus características.
     */
    @Column(length = 500)
    private String descripcion;

    /**
     * Descripción alternativa optimizada para lectores de pantalla,
     * con el fin de mejorar la accesibilidad para usuarios con discapacidad visual.
     */
    @Column(name = "descripcion_accesible", length = 500)
    private String descripcionAccesible;

    /**
     * URL pública de una imagen representativa del recurso,
     * generalmente alojada en un servicio de almacenamiento en la nube como Cloudinary.
     */
    @Column(name = "url_imagen")
    private String urlImagen;

    /**
     * Código único asociado a una etiqueta física (QR, NFC) para la identificación rápida del recurso.
     */
    @Column(name = "codigo_qr", unique = true)
    private String codigoQr;

    /**
     * Indicador para el borrado lógico. Si es {@code false}, el recurso se considera inactivo
     * y no aparecerá en listados públicos, pero se mantiene en la base de datos por integridad referencial.
     */
    private boolean activo = true;

    /**
     * Categoría a la que pertenece el recurso, definida por el enum {@link TipoRecurso}.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoRecurso tipo;

    /**
     * Condición operativa actual del recurso, definida por el enum {@link EstadoRecurso}.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRecurso estado;

    /**
     * Constructor para crear una instancia de Recurso con los campos esenciales.
     *
     * @param nombre El nombre del recurso.
     * @param tipo La categoría del recurso.
     * @param estado La condición operativa inicial del recurso.
     */
    public Recurso(String nombre, TipoRecurso tipo, EstadoRecurso estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.activo = true;
    }
}