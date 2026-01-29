package com.example.ResourcesHub.model;

import com.example.ResourcesHub.model.enums.EstadoRecurso;
import com.example.ResourcesHub.model.enums.TipoRecurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Table(name = "recursos")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Obligatorio
    private String nombre;

    @Column(length = 500) // Damos espacio para una buena descripción
    private String descripcion;

    // --- CAMPOS PRO (Diferenciadores) ---

    // ACCESIBILIDAD: Texto que leerá el móvil a los ciegos
    @Column(name = "descripcion_accesible", length = 500)
    private String descripcionAccesible;

    // CLOUDINARY: Aquí guardaremos la URL de la foto (ej: https://res.cloudinary...)
    @Column(name = "url_imagen")
    private String urlImagen;

    // QR: El código único que pegaremos en la mesa (ej: "AULA-3-NFC-88")
    @Column(name = "codigo_qr", unique = true)
    private String codigoQr;

    // BORRADO LÓGICO: Si es false, es como si no existiera, pero guardamos el dato.
    private boolean activo = true;

    // --- ENUMS (Orden y Control) ---

    @Enumerated(EnumType.STRING) // Guarda el texto "AULA" en la BD, no un número raro
    @Column(nullable = false)
    private TipoRecurso tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRecurso estado;

    // --- CONSTRUCTORES, GETTERS Y SETTERS ---

    public Recurso() {}

    public Recurso(String nombre, TipoRecurso tipo, EstadoRecurso estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.activo = true;
    }



}
