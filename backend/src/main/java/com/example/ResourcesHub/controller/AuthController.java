package com.example.ResourcesHub.controller;

import com.example.ResourcesHub.dto.AuthenticationRequest;
import com.example.ResourcesHub.dto.AuthenticationResponse;
import com.example.ResourcesHub.dto.RegisterRequest;
import com.example.ResourcesHub.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestionar los procesos de autenticación y registro de usuarios.
 * Actúa como el punto de entrada (endpoint) para que los clientes puedan registrarse en el sistema
 * y obtener un token de autenticación.
 *
 * @see AuthenticationService
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    /**
     * Procesa una solicitud de registro para un nuevo usuario.
     * Delega la lógica de creación del usuario y la generación del token JWT inicial
     * al {@link AuthenticationService}.
     *
     * @param request Un objeto {@link RegisterRequest} que contiene los datos del nuevo usuario (nombre, email, contraseña).
     * @return Un {@link ResponseEntity} que encapsula un {@link AuthenticationResponse} con el token JWT generado.
     * @throws com.example.ResourcesHub.excepciones.EmailYaRegistradoException Si el correo electrónico proporcionado ya existe en el sistema.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Procesa una solicitud de autenticación para un usuario existente.
     * Delega la validación de las credenciales (email y contraseña) y la generación del token JWT
     * al {@link AuthenticationService}.
     *
     * @param request Un objeto {@link AuthenticationRequest} que contiene las credenciales del usuario.
     * @return Un {@link ResponseEntity} que encapsula un {@link AuthenticationResponse} con el token JWT si la autenticación es exitosa.
     * @throws org.springframework.security.core.AuthenticationException Si las credenciales son incorrectas.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}