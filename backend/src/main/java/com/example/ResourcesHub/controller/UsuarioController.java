package com.example.ResourcesHub.controller;

import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 *
 * Expone endpoints HTTP para realizar operaciones CRUD sobre la entidad {@link Usuario},
 * delegando la lógica de negocio en {@link UsuarioService}. Sigue las convenciones REST
 * utilizando códigos de estado adecuados para cada operación.
 *
 * @author michael
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Crea una nueva instancia del controlador de usuarios.
     *
     * @param usuarioService servicio que contiene la lógica de negocio para usuarios
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * Maneja solicitudes GET a {@code /api/usuarios}.
     *
     * @return una lista con todos los usuarios
     */
    @GetMapping
    public List<Usuario> getAll() {
        return usuarioService.listarTodos();
    }

    /**
     * Obtiene un usuario específico por su identificador.
     *
     * Maneja solicitudes GET a {@code /api/usuarios/{id}}. Si el usuario existe,
     * devuelve estado 200 OK con el usuario en el cuerpo; si no existe,
     * devuelve estado 404 Not Found.
     *
     * @param id identificador del usuario a buscar
     * @return una respuesta HTTP con el usuario o con estado 404 si no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getOne(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo usuario.
     *
     * Maneja solicitudes POST a {@code /api/usuarios}. Si la creación es exitosa,
     * devuelve estado 201 Created con el usuario creado en el cuerpo.
     *
     * @param usuario datos del usuario a crear
     * @return una respuesta HTTP con el usuario creado y estado 201 Created
     */
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * Maneja solicitudes PUT a {@code /api/usuarios/{id}}. Si el usuario existe y se
     * actualiza correctamente, devuelve estado 200 OK con el usuario actualizado;
     * si no existe, devuelve estado 404 Not Found.
     *
     * @param id identificador del usuario a actualizar
     * @param usuario objeto con los nuevos datos del usuario
     * @return una respuesta HTTP con el usuario actualizado o estado 404 si no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * Maneja solicitudes DELETE a {@code /api/usuarios/{id}}. Si el usuario existe y se
     * elimina correctamente, devuelve estado 204 No Content; si no existe, devuelve
     * estado 404 Not Found.
     *
     * @param id identificador del usuario a eliminar
     * @return una respuesta HTTP con estado 204 si se borró o 404 si no se encontró
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioService.borrar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
