package com.example.ResourcesHub.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa errores de Lógica de Negocio (Conflictos, Validaciones)
    // Usamos IllegalStateException para cuando el recurso está ocupado
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(IllegalStateException ex) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage()); // Devuelve 409
    }

    // 2. Atrapa errores de Argumentos (Fechas al revés, datos nulos)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage()); // Devuelve 400
    }

    // --- MÉTODOS AUXILIARES ---

    // Método privado para fabricar el JSON
    private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus estado, String mensaje) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", estado.value());
        errorBody.put("error", estado.getReasonPhrase());
        errorBody.put("message", mensaje); // <--- Aquí va nuestro mensaje personalizado

        return new ResponseEntity<>(errorBody, estado);
    }
}