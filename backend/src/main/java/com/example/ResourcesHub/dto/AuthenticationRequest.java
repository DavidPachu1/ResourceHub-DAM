package com.example.ResourcesHub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos (DTO) para encapsular las credenciales de una solicitud de autenticación.
 * Este objeto es utilizado por el {@link com.example.ResourcesHub.controller.AuthController}
 * para recibir los datos necesarios para verificar la identidad de un usuario.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    /**
     * La dirección de correo electrónico (nombre de usuario) del usuario que intenta autenticarse.
     */
    private String email;

    /**
     * La contraseña del usuario en texto plano. El servicio de autenticación se encargará
     * de compararla con la versión codificada almacenada en la base de datos.
     */
    private String password;
}