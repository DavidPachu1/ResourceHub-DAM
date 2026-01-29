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
    public ResponseEntity<?> crear(@RequestBody Reserva reserva) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.guardar(reserva));
        } catch (IllegalArgumentException e) {
            // Si fallan las validaciones de fecha, devolvemos un error 400 (Bad Request)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}