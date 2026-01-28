package com.example.ResourcesHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Representa un usuario del sistema almacenado en la base de datos.
 *
 * Contiene información básica de identificación como el nombre y el correo
 * electrónico, y se mapea a la tabla {@code usuarios} mediante JPA/Hibernate.
 * La gestión de getters, setters y constructores se realiza con Lombok.
 *
 * @author michael
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    /**
     * Identificador único del usuario en la base de datos.
     *
     * Se genera automáticamente mediante la estrategia {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre del usuario.
     *
     * Este campo es obligatorio.
     */
    @Column(nullable = false)
    private String nombre;
    /**
     * Correo electrónico del usuario.
     *
     * Este campo es obligatorio y debe ser único en la base de datos.
     */
    @Column(nullable = false, unique = true)
    private String email;
    /**
     * Correo password del usuario.
     *
     * Este campo es obligatorio y debe ser único en la base de datos.
     */
    @Column(nullable = false)
    private String password;


}
