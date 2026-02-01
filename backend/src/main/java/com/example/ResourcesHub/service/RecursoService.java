package com.example.ResourcesHub.service;

import com.example.ResourcesHub.model.Recurso;
import com.example.ResourcesHub.repository.RecursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de Recursos.
 * Esta clase centraliza la lógica para interactuar con los recursos de la plataforma,
 * como listarlos, crearlos, editarlos y realizar borrados lógicos.
 */
@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    /**
     * Recupera una lista de todos los recursos que se encuentran actualmente activos.
     * Este método filtra los recursos que han sido marcados como inactivos (borrado lógico).
     *
     * @return Una lista de objetos {@link Recurso} activos.
     */
    public List<Recurso> listarTodos() {
        return recursoRepository.findByActivoTrue();
    }

    /**
     * Persiste un nuevo recurso o actualiza uno existente en la base de datos.
     *
     * @param recurso El objeto {@link Recurso} a guardar.
     * @return El recurso guardado, con su ID asignado si es una nueva creación.
     */
    public Recurso guardar(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    /**
     * Busca un recurso específico por su identificador único.
     *
     * @param id El ID del recurso a buscar.
     * @return Un {@link Optional} que contiene el recurso si se encuentra, o vacío si no.
     */
    public Optional<Recurso> buscarPorId(Long id) {
        return recursoRepository.findById(id);
    }

    /**
     * Realiza un borrado lógico de un recurso, marcándolo como inactivo.
     * El recurso no se elimina físicamente de la base de datos, sino que se actualiza su estado.
     * Este enfoque preserva la integridad de los datos históricos, como las reservas pasadas asociadas a él.
     *
     * @param id El ID del recurso a desactivar.
     * @return {@code true} si el recurso fue encontrado y marcado como inactivo, {@code false} en caso contrario.
     */
    public boolean borrar(Long id) {
        return recursoRepository.findById(id).map(recurso -> {
            recurso.setActivo(false); // 👻 Lo volvemos un fantasma
            recursoRepository.save(recurso); // Guardamos el cambio
            return true;
        }).orElse(false);
    }
}