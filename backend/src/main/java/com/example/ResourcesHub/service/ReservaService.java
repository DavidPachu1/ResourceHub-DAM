package com.example.ResourcesHub.service;

import com.example.ResourcesHub.model.enums.EstadoReserva; // Asegúrate de tener este Enum o cambia a String si usas texto
import com.example.ResourcesHub.model.Reserva;
import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.repository.ReservaRepository;
import com.example.ResourcesHub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository; // 1. 👇 ¡NUEVO! Necesitamos esto para buscar al usuario

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    // Cambiamos el nombre a 'crearReserva' y pedimos el email
    public Reserva crearReserva(Reserva reserva, String emailUsuario) {

        // 2. 👇 BUSCAMOS AL USUARIO REAL (Seguridad)
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + emailUsuario));

        // 3. 👇 Validaciones de Fechas
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio");
        }

        // 4. 👇 Validaciones de Conflicto (Solapamiento)
        List<Reserva> conflictos = reservaRepository.findReservasEnConflicto(
                reserva.getRecurso().getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin()
        );

        if (!conflictos.isEmpty()) {
            throw new IllegalStateException("El recurso ya está reservado en ese horario.");
        }

        // 5. 👇 ASIGNACIÓN AUTOMÁTICA (La magia)
        reserva.setUsuario(usuario); // ¡Aquí forzamos que la reserva sea del dueño del token!
        reserva.setEstado(EstadoReserva.PENDIENTE); // Estado inicial por defecto

        return reservaRepository.save(reserva);
    }


}