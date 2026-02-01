package com.example.ResourcesHub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos (DTO) para encapsular la información de una solicitud de registro.
 * Este objeto es utilizado por el {@link com.example.ResourcesHub.controller.AuthController}
 * para recibir los datos necesarios para crear un nuevo usuario en el sistema.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    /**
     * El nombre completo del usuario que desea registrarse.
     */
    private String nombre;

    /**
     * La dirección de correo electrónico del usuario. Debe ser única en el sistema
     * y se utilizará como nombre de usuario para la autenticación.
     */
    private String email;

    /**
     * La contraseña elegida por el usuario. Se espera que llegue en texto plano
     * desde el cliente y será codificada por el servicio antes de ser persistida.
     */
    private String password;
}