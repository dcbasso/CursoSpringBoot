package com.dante.curso.services;

import com.dante.curso.domain.Categoria;
import com.dante.curso.domain.Produto;
import com.dante.curso.exceptions.ObjectNotFoundException;
import com.dante.curso.repositories.CategoriaRepository;
import com.dante.curso.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ProdutoService(final ProdutoRepository produtoRepository, final CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto find(final Integer id) {
        final Optional<Produto> optionalProduto = produtoRepository.findById(id);
        return optionalProduto.orElseThrow(() ->
                new ObjectNotFoundException("Object not found. ID: " + id + ", Type: " + Produto.class.getName())
        );
    }

    public Page<Produto> search(final String nome, List<Integer> categoriaIds, final Integer page, final Integer itensPerPage, final String orderBy, final String direction) {
        final PageRequest pageRequest = PageRequest.of(page, itensPerPage, Sort.Direction.valueOf(direction), orderBy);
        final List<Categoria> categorias = this.categoriaRepository.findAllById(categoriaIds);
        return this.produtoRepository.search(nome, categorias, pageRequest);
    }

}
