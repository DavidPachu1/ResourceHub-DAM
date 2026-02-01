package com.example.ResourcesHub.model.enums;

/**
 * Define los roles de autorización dentro del sistema.
 * Estos roles son utilizados por Spring Security para controlar el acceso a los endpoints
 * y a las operaciones de negocio críticas.
 *
 * @see org.springframework.security.core.GrantedAuthority
 */
public enum Rol {
    /**
     * Rol estándar para los usuarios registrados. Permite realizar operaciones básicas
     * como solicitar reservas y consultar recursos.
     */
    USER,

    /**
     * Rol de administrador con privilegios elevados. Permite gestionar usuarios,
     * recursos y aprobar o rechazar reservas.
     */
    ADMIN
}