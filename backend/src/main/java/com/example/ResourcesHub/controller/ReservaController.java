package com.example.ResourcesHub.controller;

import com.example.ResourcesHub.model.Reserva;
import com.example.ResourcesHub.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva, java.security.Principal principal) {
        // 'principal' es inyectado por Spring Security y contiene los datos del Token
        String email = principal.getName();

        // Pasamos el email al servicio
        Reserva nuevaReserva = reservaService.crearReserva(reserva, email);

        return ResponseEntity.ok(nuevaReserva);
    }
}