package com.example.ResourcesHub.service;

import com.example.ResourcesHub.model.enums.EstadoReserva;
import com.example.ResourcesHub.model.Reserva;
import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.repository.ReservaRepository;
import com.example.ResourcesHub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de Reservas.
 * Esta clase encapsula toda la lógica de negocio relacionada con la creación,
 * consulta y validación de las reservas de recursos. Actúa como intermediario
 * entre el controlador y el repositorio.
 */
@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene una lista con todas las reservas registradas en el sistema.
     *
     * @return Una lista de objetos {@link Reserva}.
     */
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    /**
     * Busca una reserva específica a través de su identificador único.
     *
     * @param id El ID de la reserva a buscar.
     * @return Un {@link Optional} que contiene la reserva si se encuentra, o vacío en caso contrario.
     */
    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    /**
     * Orquesta la creación de una nueva reserva, aplicando validaciones de negocio críticas.
     * Asocia la reserva al usuario solicitante, valida la consistencia de las fechas y
     * previene conflictos con otras reservas existentes para el mismo recurso.
     *
     * @param reserva El objeto de reserva con los datos iniciales (recurso, fechas).
     * @param emailUsuario El email del usuario que realiza la reserva, extraído del token de seguridad.
     * @return La entidad {@link Reserva} persistida, con el usuario asignado y el estado inicial establecido.
     * @throws RuntimeException Si no se encuentra un usuario con el email proporcionado.
     * @throws IllegalArgumentException Si la fecha de fin de la reserva es anterior a la fecha de inicio.
     * @throws IllegalStateException Si el recurso ya se encuentra reservado en el intervalo de tiempo solicitado.
     */
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