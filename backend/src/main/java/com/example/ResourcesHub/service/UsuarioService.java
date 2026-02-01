package com.example.ResourcesHub.service;

import com.example.ResourcesHub.model.Usuario;
import com.example.ResourcesHub.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de usuarios.
 *
 * Proporciona operaciones de alto nivel para listar, buscar, crear, actualizar
 * y eliminar usuarios, delegando el acceso a datos al repositorio {@code UsuarioRepository}.
 * Aquí se puede añadir lógica adicional, como validaciones o reglas de seguridad.
 *
 * autor michael
 */
@Service // Marca de lógica de negocio
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Crea una nueva instancia del servicio de usuarios.
     *
     * @param usuarioRepository repositorio utilizado para acceder a los datos de usuarios
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return una lista con todos los usuarios almacenados
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id identificador del usuario a buscar
     * @return un {@code Optional} que contiene el usuario si existe,
     *         o vacío si no se encuentra
     */
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Guarda un nuevo usuario en el sistema.
     *
     * @param usuario el usuario a guardar
     * @return el usuario guardado, incluyendo su identificador generado
     */
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * Solo se actualizan campos seguros, como el nombre y el correo electrónico;
     * la contraseña u otros datos sensibles no se modifican aquí por motivos de seguridad.
     *
     * @param id identificador del usuario a actualizar
     * @param usuarioActualizado objeto con los nuevos datos del usuario
     * @return un {@code Optional} que contiene el usuario actualizado si existe,
     *         o vacío si no se encuentra el usuario con el {@code id} indicado
     */
    public Optional<Usuario> actualizar(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
            // NO tocamos la contraseña aquí por seguridad
            return usuarioRepository.save(usuarioExistente);
        });
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * Primero verifica si el usuario existe antes de intentar borrarlo.
     *
     * @param id identificador del usuario a eliminar
     * @return {@code true} si el usuario fue eliminado,
     *         {@code false} si no existía un usuario con ese {@code id}
     */
    public boolean borrar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

