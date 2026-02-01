package com.example.ResourcesHub.controller;

import com.example.ResourcesHub.model.Recurso;
import com.example.ResourcesHub.service.RecursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    // 1. OBTENER TODOS (Solo los activos)
    @GetMapping
    public List<Recurso> listar() {
        return recursoService.listarTodos();
    }

    // 2. CREAR UNO NUEVO
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Recurso> crear(@RequestBody Recurso recurso) {
        Recurso nuevo = recursoService.guardar(recurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // 3. ACTUALIZAR (PUT)
    // Nota: Aquí sobrescribimos los datos viejos con los nuevos
    @PutMapping("/{id}")
    public ResponseEntity<Recurso> actualizar(@PathVariable Long id, @RequestBody Recurso recursoEditado) {
        return recursoService.buscarPorId(id)
                .map(recursoExistente -> {
                    // Actualizamos campos
                    recursoExistente.setNombre(recursoEditado.getNombre());
                    recursoExistente.setDescripcion(recursoEditado.getDescripcion());
                    recursoExistente.setDescripcionAccesible(recursoEditado.getDescripcionAccesible());
                    recursoExistente.setUrlImagen(recursoEditado.getUrlImagen());
                    recursoExistente.setCodigoQr(recursoEditado.getCodigoQr());
                    recursoExistente.setTipo(recursoEditado.getTipo());
                    recursoExistente.setEstado(recursoEditado.getEstado());
                    // Guardamos
                    return ResponseEntity.ok(recursoService.guardar(recursoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. BORRAR (Lógico - Desactivar)
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (recursoService.borrar(id)) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }
}