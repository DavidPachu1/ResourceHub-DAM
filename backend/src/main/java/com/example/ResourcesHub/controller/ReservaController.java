package com.example.ResourcesHub.controller;

import com.example.ResourcesHub.model.Reserva;
import com.example.ResourcesHub.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones sobre las reservas de recursos.
 * Expone los endpoints para crear, listar y administrar las reservas, actuando como la
 * capa de entrada para todas las interacciones del cliente relacionadas con las mismas.
 * Requiere que el usuario esté autenticado para la mayoría de las operaciones.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * Recupera un listado completo de todas las reservas existentes en el sistema.
     * Este método puede ser utilizado para propósitos de administración o auditoría.
     *
     * @return Una lista de objetos {@link Reserva} que representan todas las reservas.
     */
    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listarTodas();
    }

    /**
     * Procesa la solicitud para crear una nueva reserva de un recurso.
     * La reserva se asocia automáticamente al usuario autenticado que realiza la solicitud.
     * La lógica de negocio, como la validación de disponibilidad del recurso, es delegada al {@link ReservaService}.
     *
     * @param reserva El objeto {@link Reserva} con los detalles de la reserva a crear (ej: ID del recurso, fechas).
     * @param principal Objeto inyectado por Spring Security que representa al usuario autenticado. Se utiliza para obtener el email del solicitante.
     * @return Un {@link ResponseEntity} con el objeto {@link Reserva} recién creado y un estado HTTP 200 (OK).
     * @throws com.example.ResourcesHub.excepciones.RecursoNoEncontradoException Si el recurso especificado en la reserva no existe.
     * @throws com.example.ResourcesHub.excepciones.ReservaInvalidaException Si las fechas de la reserva no son válidas o el recurso ya está reservado en ese período.
     */
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva, java.security.Principal principal) {
        // 'principal' es inyectado por Spring Security y contiene los datos del Token
        String email = principal.getName();

        // Pasamos el email al servicio
        Reserva nuevaReserva = reservaService.crearReserva(reserva, email);

        return ResponseEntity.ok(nuevaReserva);
    }
}