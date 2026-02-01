package com.example.ResourcesHub.controller;

import com.example.ResourcesHub.model.Recurso;
import com.example.ResourcesHub.service.RecursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión del ciclo de vida de los {@link Recurso}s.
 * Expone los endpoints para listar, crear, actualizar y eliminar (lógicamente) los recursos
 * de la plataforma. Actúa como la capa de entrada para todas las operaciones CRUD sobre los recursos.
 *
 * @see RecursoService
 * @see Recurso
 */
@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    /**
     * Recupera una lista de todos los recursos que se encuentran activos en el sistema.
     * La obtención de los datos es delegada al {@link RecursoService}, que filtra los inactivos.
     *
     * @return Una lista de objetos {@link Recurso} activos.
     */
    @GetMapping
    public List<Recurso> listar() {
        return recursoService.listarTodos();
    }

    /**
     * Endpoint para la creación de un nuevo recurso.
     * La persistencia del nuevo recurso es gestionada por el {@link RecursoService}.
     *
     * @apiNote La ejecución de este método requiere que el usuario autenticado posea la autoridad 'ADMIN'.
     * @param recurso El objeto {@link Recurso} a crear, recibido en el cuerpo de la solicitud.
     * @return Un {@link ResponseEntity} con el recurso recién creado y un estado HTTP 201 (Created).
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Recurso> crear(@RequestBody Recurso recurso) {
        Recurso nuevo = recursoService.guardar(recurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /**
     * Actualiza la información de un recurso existente, identificado por su ID.
     * Este método realiza una actualización completa del objeto.
     *
     * @apiNote La ejecución de este método requiere que el usuario autenticado posea la autoridad 'ADMIN'.
     * @param id El identificador único del recurso a actualizar.
     * @param recursoEditado El objeto {@link Recurso} con los nuevos datos.
     * @return Un {@link ResponseEntity} con el recurso actualizado y estado HTTP 200 (OK),
     *         o un estado HTTP 404 (Not Found) si el recurso no existe.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Recurso> actualizar(@PathVariable Long id, @RequestBody Recurso recursoEditado) {
        return recursoService.buscarPorId(id)
                .map(recursoExistente -> {
                    recursoExistente.setNombre(recursoEditado.getNombre());
                    recursoExistente.setDescripcion(recursoEditado.getDescripcion());
                    recursoExistente.setDescripcionAccesible(recursoEditado.getDescripcionAccesible());
                    recursoExistente.setUrlImagen(recursoEditado.getUrlImagen());
                    recursoExistente.setCodigoQr(recursoEditado.getCodigoQr());
                    recursoExistente.setTipo(recursoEditado.getTipo());
                    recursoExistente.setEstado(recursoEditado.getEstado());
                    return ResponseEntity.ok(recursoService.guardar(recursoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza un borrado lógico de un recurso, marcándolo como inactivo.
     * El {@link RecursoService} se encarga de cambiar el estado del recurso sin eliminarlo de la base de datos.
     *
     * @apiNote La ejecución de este método requiere que el usuario autenticado posea la autoridad 'ADMIN'.
     * @param id El identificador único del recurso a desactivar.
     * @return Un {@link ResponseEntity} con estado HTTP 204 (No Content) si el borrado fue exitoso,
     *         o HTTP 404 (Not Found) si el recurso no se encontró.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (recursoService.borrar(id)) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }
}