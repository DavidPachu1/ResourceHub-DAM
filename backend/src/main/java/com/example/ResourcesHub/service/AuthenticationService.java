package com.example.ResourcesHub.service;

import com.example.ResourcesHub.config.JwtService;
import com.example.ResourcesHub.dto.AuthenticationRequest;
import com.example.ResourcesHub.dto.AuthenticationResponse;
import com.example.ResourcesHub.dto.RegisterRequest;
import com.example.ResourcesHub.model.enums.Rol;
import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = new Usuario();
        user.setNombre(request.getNombre());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 🔒 Encriptamos aquí
        user.setRol(Rol.USER); // Por defecto todos son USER

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        // Si llega aquí, es que el usuario y pass son correctos
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
