package com.dante.curso.resouces;

import com.dante.curso.domain.Categoria;
import com.dante.curso.domain.Produto;
import com.dante.curso.dto.CategoriaDTO;
import com.dante.curso.dto.ProdutoDTO;
import com.dante.curso.resouces.utils.URL;
import com.dante.curso.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoResource(final ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Produto> find(@PathVariable Integer id) {
        final Produto produto = this.produtoService.find(id);
        return ResponseEntity.ok(produto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPaged(
            @RequestParam(value = "nome", defaultValue = "") final String nome,
            @RequestParam(value = "categorias", defaultValue = "") final String categorias,
            @RequestParam(value = "page", defaultValue = "0") final Integer page,
            @RequestParam(value = "itensPerPage", defaultValue = "24") final Integer itensPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") final String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") final String direction) {
        final List<Integer> categoriasList = URL.decodeIntegerList(categorias);
        final String nomeDecoded = URL.decodeString(nome);
        final Page<Produto> paged = this.produtoService.search(nomeDecoded, categoriasList, page, itensPerPage, orderBy, direction);
        final Page<ProdutoDTO> pagedDTO = paged.map(ProdutoDTO::new);
        return ResponseEntity.ok(pagedDTO);
    }

}
