package com.dante.curso.resouces;

import com.dante.curso.domain.Categoria;
import com.dante.curso.dto.CategoriaDTO;
import com.dante.curso.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaResource(final CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        final List<Categoria> categoriaList = this.categoriaService.findAll();
        final List<CategoriaDTO> categoriaDTOS = categoriaList.stream().map(cat -> new CategoriaDTO(cat)).collect(Collectors.toList());
        return ResponseEntity.ok(categoriaDTOS);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Categoria> find(@PathVariable final Integer id) {
        final Categoria categoria = this.categoriaService.find(id);
        return ResponseEntity.ok(categoria);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria =  this.categoriaService.fromDTO(categoriaDTO);
        categoria = this.categoriaService.insert(categoria);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PUT, value= "/{id}")
    public ResponseEntity<Void> update(@PathVariable final Integer id, @Valid @RequestBody final CategoriaDTO categoriaDTO) {
        final Categoria categoria = this.categoriaService.fromDTO(categoriaDTO);
        categoria.setId(id);
        this.categoriaService.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id) {
        this.categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/paged")
    public ResponseEntity<Page<CategoriaDTO>> findPaged(@RequestParam(value = "page", defaultValue = "0") final Integer page,
                                          @RequestParam(value = "itensPerPage", defaultValue = "24") final Integer itensPerPage,
                                          @RequestParam(value = "orderBy", defaultValue = "nome") final String orderBy,
                                          @RequestParam(value = "direction", defaultValue = "ASC") final String direction) {
        final Page<Categoria> paged = this.categoriaService.findPaged(page, itensPerPage, orderBy, direction);
        final Page<CategoriaDTO> pagedDTO = paged.map(cat -> new CategoriaDTO(cat));
        return ResponseEntity.ok(pagedDTO);
    }

}
