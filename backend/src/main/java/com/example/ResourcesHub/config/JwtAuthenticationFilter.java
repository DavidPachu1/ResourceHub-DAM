package com.example.ResourcesHub.config;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación que intercepta las solicitudes HTTP para procesar el token JWT.
 * Este filtro se ejecuta una vez por cada solicitud y es el responsable de validar el token
 * y establecer el contexto de seguridad de Spring si el token es válido.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Procesa la solicitud HTTP para extraer y validar el token JWT del encabezado de autorización.
     * Si el token es válido, autentica al usuario en el contexto de seguridad de Spring.
     *
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros a la que se debe pasar la solicitud.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Si no hay encabezado de autorización o no empieza con "Bearer ", se pasa al siguiente filtro.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Extrae el token eliminando el prefijo "Bearer ".
        userEmail = jwtService.extractUsername(jwt); // Extrae el email del usuario del token.

        // Si se extrae el email y no hay una autenticación previa en el contexto de seguridad.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Si el token es válido para el usuario.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Crea un token de autenticación y lo establece en el contexto de seguridad.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continúa con la cadena de filtros.
        filterChain.doFilter(request, response);
    }
}