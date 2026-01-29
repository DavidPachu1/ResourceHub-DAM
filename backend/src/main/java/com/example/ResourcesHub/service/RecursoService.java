package com.example.ResourcesHub.service;

import com.example.ResourcesHub.model.Recurso;
import com.example.ResourcesHub.repositoy.RecursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    // 1. LISTAR (Solo los activos, para eso creamos el método mágico antes)
    public List<Recurso> listarTodos() {
        return recursoRepository.findByActivoTrue();
    }

    // 2. GUARDAR (Crear o Editar)
    public Recurso guardar(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    // 3. BUSCAR POR ID
    public Optional<Recurso> buscarPorId(Long id) {
        return recursoRepository.findById(id);
    }

    // 4. BORRADO LÓGICO (Nivel Enterprise 🏢)
    public boolean borrar(Long id) {
        return recursoRepository.findById(id).map(recurso -> {
            recurso.setActivo(false); // 👻 Lo volvemos un fantasma
            recursoRepository.save(recurso); // Guardamos el cambio
            return true;
        }).orElse(false);
    }
}