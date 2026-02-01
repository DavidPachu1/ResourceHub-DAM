package com.example.ResourcesHub.model;

import com.example.ResourcesHub.model.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa a un usuario del sistema.
 * Esta clase implementa la interfaz {@link UserDetails} de Spring Security para integrarse
 * directamente con el mecanismo de autenticación y autorización del framework.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    /**
     * Identificador único del usuario, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Dirección de correo electrónico del usuario. Actúa como el nombre de usuario
     * para la autenticación y debe ser único en el sistema.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Nombre completo del usuario.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Contraseña del usuario almacenada de forma segura (encriptada) en la base de datos.
     */
    @Column(nullable = false)
    private String password;

    /**
     * El rol asignado al usuario, que define sus permisos en el sistema.
     * @see Rol
     */
    @Enumerated(EnumType.STRING)
    private Rol rol;


    /**
     * Devuelve las autoridades concedidas al usuario.
     * Spring Security utiliza esta información para las comprobaciones de autorización.
     *
     * @return Una colección que contiene la autoridad única del usuario basada en su {@link Rol}.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    /**
     * Devuelve el nombre de usuario utilizado para autenticar al usuario.
     * En esta implementación, corresponde al email.
     *
     * @return El email del usuario.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indica si la cuenta del usuario ha expirado. Una cuenta expirada no puede ser autenticada.
     * @return {@code true} ya que las cuentas en este sistema no expiran.
     */
    @Override
    public boolean isAccountNonExpired() { return true; }

    /**
     * Indica si el usuario está bloqueado o desbloqueado. Un usuario bloqueado no puede ser autenticado.
     * @return {@code true} ya que las cuentas en este sistema no se bloquean.
     */
    @Override
    public boolean isAccountNonLocked() { return true; }

    /**
     * Indica si las credenciales del usuario (contraseña) han expirado.
     * @return {@code true} ya que las credenciales en este sistema no expiran.
     */
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    /**
     * Indica si el usuario está habilitado o deshabilitado. Un usuario deshabilitado no puede ser autenticado.
     * @return {@code true} ya que los usuarios están siempre habilitados por defecto.
     */
    @Override
    public boolean isEnabled() { return true; }
}