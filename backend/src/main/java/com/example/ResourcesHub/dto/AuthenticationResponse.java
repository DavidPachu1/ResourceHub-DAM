package com.example.ResourcesHub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos (DTO) para encapsular la respuesta de una operación de autenticación o registro exitosa.
 * Este objeto es devuelto por el {@link com.example.ResourcesHub.controller.AuthController}
 * y contiene el token de acceso que el cliente debe utilizar para las solicitudes subsiguientes.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    /**
     * El JSON Web Token (JWT) generado para el usuario autenticado.
     * Este token debe ser incluido en el encabezado 'Authorization' de las solicitudes
     * a los endpoints protegidos, precedido por el esquema "Bearer ".
     */
    private String token;
}