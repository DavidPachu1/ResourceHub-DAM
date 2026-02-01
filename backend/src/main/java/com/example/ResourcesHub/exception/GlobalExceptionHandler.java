package com.example.ResourcesHub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador centralizado de excepciones para toda la API REST.
 * <p>
 * Esta clase utiliza el patrón AOP (Programación Orientada a Aspectos) mediante {@link RestControllerAdvice}
 * para interceptar las excepciones lanzadas desde cualquier Controlador o Servicio.
 * <p>
 * Su responsabilidad principal es <b>traducir</b> errores de Java (Stack Traces) a respuestas HTTP estandarizadas
 * y amigables para el cliente (JSON), garantizando que la API siempre devuelva una estructura de error consistente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Intercepta errores de estado ilegal o conflictos de lógica de negocio.
     * <p>
     * Se activa, por ejemplo, cuando se intenta reservar un recurso que ya está ocupado en ese horario
     * (solapamiento) o cuando se intenta realizar una operación no permitida por el estado actual de la entidad.
     * <p>
     * Mapea la excepción a un código de estado <b>HTTP 409 (Conflict)</b>, indicando que la petición
     * es correcta sintácticamente pero incompatible con el estado actual del servidor.
     *
     * @param ex La excepción capturada con el mensaje de error de negocio.
     * @return Una respuesta JSON estructurada con el motivo del conflicto.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(IllegalStateException ex) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Intercepta errores de argumentos inválidos o validaciones lógicas fallidas.
     * <p>
     * Se activa cuando los datos enviados por el cliente no superan las validaciones del servicio
     * (ej: fecha de fin anterior a la fecha de inicio, o datos obligatorios nulos).
     * <p>
     * Mapea la excepción a un código de estado <b>HTTP 400 (Bad Request)</b>, indicando al cliente
     * que debe corregir los datos de su petición antes de reintentar.
     *
     * @param ex La excepción capturada con los detalles de la validación fallida.
     * @return Una respuesta JSON estructurada explicando qué dato es incorrecto.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Método utilitario ("Factory method") para estandarizar la estructura del cuerpo de error.
     * <p>
     * Centraliza la creación del mapa JSON para asegurar que todos los endpoints de error
     * devuelvan siempre los mismos campos (timestamp, status, error, message).
     *
     * @param estado  El código de estado HTTP (Enum {@link HttpStatus}).
     * @param mensaje El mensaje descriptivo del error destinado al desarrollador cliente.
     * @return Un objeto ResponseEntity listo para ser enviado al cliente.
     */
    private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus estado, String mensaje) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", estado.value());
        errorBody.put("error", estado.getReasonPhrase());
        errorBody.put("message", mensaje);

        return new ResponseEntity<>(errorBody, estado);
    }
}