package com.example.ResourcesHub.service;

import com.example.ResourcesHub.model.Reserva;
import com.example.ResourcesHub.repository.ReservaRepository;
import lombok.RequiredArgsConstructor; // Importante: Importar Lombok
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva guardar(Reserva reserva) {
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio");
        }

        List<Reserva> conflictos = reservaRepository.findReservasEnConflicto(
                reserva.getRecurso().getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin()
        );
        if (!conflictos.isEmpty()) {
            throw new IllegalStateException("El recurso ya está reservado en ese horario.");
        }

        return reservaRepository.save(reserva);
    }
    }