package com.example.ResourcesHub.controller;

import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la administración de la entidad {@link Usuario}.
 * Proporciona endpoints protegidos para realizar operaciones CRUD sobre los usuarios del sistema.
 * La responsabilidad de esta clase es exponer una API segura para la gestión de usuarios,
 * delegando toda la lógica de negocio al {@link UsuarioService}.
 *
 * @see UsuarioService
 * @see Usuario
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Inyecta la dependencia del servicio de usuarios.
     *
     * @param usuarioService El servicio que encapsula la lógica de negocio para los usuarios.
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Recupera una lista completa de todos los usuarios registrados en el sistema.
     *
     * @apiNote La ejecución de este método requiere que el usuario autenticado posea la autoridad 'ADMIN'.
     * @return Una lista con todos los objetos {@link Usuario}.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Usuario> getAll() {
        return usuarioService.listarTodos();
    }

    /**
     * Busca y recupera un usuario específico a través de su identificador único.
     *
     * @apiNote La ejecución de este método requiere que el usuario autenticado posea la autoridad 'ADMIN'.
     * @param id El identificador del usuario a buscar.
     * @return Un {@link ResponseEntity} con el {@link Usuario} encontrado y estado HTTP 200 (OK),
     *         o un estado HTTP 404 (Not Found) si el usuario no existe.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Usuario> getOne(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo usuario en el sistema. Este endpoint está diseñado para ser utilizado
     * por un administrador para crear cuentas manualmente.
     *
     * @apiNote La ejecución de este método requiere que el usuario autenticado posea la autoridad 'ADMIN'.
     * @param usuario El objeto {@link Usuario} con los datos a persistir.
     * @return Un {@link ResponseEntity} con el usuario recién creado y un estado HTTP 201 (Created).
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /**
     * Actualiza los datos de un usuario existente, identificado por su ID.
     *
     * @apiNote La ejecución de este método requiere que el usuario autenticado posea la autoridad 'ADMIN'.
     * @param id El identificador del usuario a actualizar.
     * @param usuario El objeto {@link Usuario} con la información actualizada.
     * @return Un {@link ResponseEntity} con el usuario actualizado y estado HTTP 200 (OK),
     *         o un estado HTTP 404 (Not Found) si el usuario no existe.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un usuario del sistema de forma permanente.
     *
     * @apiNote La ejecución de este método requiere que el usuario autenticado posea la autoridad 'ADMIN'.
     * @param id El identificador del usuario a eliminar.
     * @return Un {@link ResponseEntity} con estado HTTP 204 (No Content) si la eliminación fue exitosa,
     *         o HTTP 404 (Not Found) si el usuario no se encontró.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioService.borrar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}