package com.dante.curso.services;

import com.dante.curso.domain.Categoria;
import com.dante.curso.exceptions.ObjectNotFoundException;
import com.dante.curso.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(final CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria buscar(final Integer id) {
        final Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        return optionalCategoria.orElseThrow(() ->
                new ObjectNotFoundException("Object not found. ID: " + id + ", Type: " + Categoria.class.getName())
        );
    }

}
