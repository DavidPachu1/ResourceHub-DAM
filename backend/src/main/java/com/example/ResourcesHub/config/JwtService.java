package com.example.ResourcesHub.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para la gestión de JSON Web Tokens (JWT).
 * Se encarga de la generación, validación y extracción de información de los tokens JWT,
 * que son el núcleo del mecanismo de autenticación y autorización sin estado de la aplicación.
 */
@Service
public class JwtService {

    // Clave secreta (en producción va en variables de entorno)
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    // tiene al menos 256 bits de seguridad para el algoritmo (HS256).

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     *
     * @param token El token JWT del cual extraer el nombre de usuario.
     * @return El nombre de usuario contenido en el token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Función genérica para extraer una "claim" (afirmación) específica de un token JWT.
     *
     * @param token El token JWT.
     * @param claimsResolver Una función que define cómo extraer la claim deseada.
     * @param <T> El tipo de dato de la claim a extraer.
     * @return La claim extraída.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un nuevo token JWT para un usuario, sin claims adicionales.
     *
     * @param userDetails Los detalles del usuario para quien se genera el token.
     * @return El token JWT como una cadena de texto.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un nuevo token JWT para un usuario, permitiendo la inclusión de claims adicionales.
     *
     * @param extraClaims Un mapa de claims adicionales para incluir en el payload del token.
     * @param userDetails Los detalles del usuario para quien se genera el token.
     * @return El token JWT como una cadena de texto.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida si un token JWT es válido para un usuario específico.
     * La validación comprueba que el nombre de usuario en el token coincida y que el token no haya expirado.
     *
     * @param token El token JWT a validar.
     * @param userDetails Los detalles del usuario contra el cual se valida el token.
     * @return {@code true} si el token es válido, {@code false} en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Comprueba si un token JWT ha expirado.
     *
     * @param token El token JWT a comprobar.
     * @return {@code true} si el token ha expirado, {@code false} si todavía es válido.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     *
     * @param token El token JWT.
     * @return La fecha de expiración del token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todas las claims de un token JWT.
     * Este método es el responsable de parsear el token y verificar su firma.
     *
     * @param token El token JWT a parsear.
     * @return Un objeto {@link Claims} que contiene todas las afirmaciones del token.
     * @throws io.jsonwebtoken.JwtException Si el token no es válido (firma incorrecta, malformado, etc.).
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene la clave de firma (signing key) a partir de la clave secreta codificada en Base64.
     *
     * @return La clave de firma para el algoritmo HMAC-SHA.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}