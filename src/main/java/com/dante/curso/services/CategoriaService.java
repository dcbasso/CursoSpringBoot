package com.dante.curso.services;

import com.dante.curso.domain.Categoria;
import com.dante.curso.exceptions.DataIntegrityException;
import com.dante.curso.exceptions.ObjectNotFoundException;
import com.dante.curso.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(final CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria find(final Integer id) {
        final Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        return optionalCategoria.orElseThrow(() ->
                new ObjectNotFoundException("Object not found. ID: " + id + ", Type: " + Categoria.class.getName())
        );
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria insert(final Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria update(final Categoria categoria) {
        this.find(categoria.getId());
        return this.categoriaRepository.save(categoria);
    }

    public void delete(final Integer id) {
        this.find(id);
        try {
            this.categoriaRepository.deleteById(id);
        } catch (final DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DataIntegrityException("Was not possible to exclude Categoria");
        }
    }

}
