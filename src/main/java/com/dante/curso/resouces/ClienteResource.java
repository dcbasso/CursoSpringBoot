package com.dante.curso.resouces;

import com.dante.curso.domain.Cliente;
import com.dante.curso.dto.ClienteDTO;
import com.dante.curso.dto.ClienteNewDTO;
import com.dante.curso.services.ClienteService;
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
@RequestMapping("/clientes")
public class ClienteResource {

    private final ClienteService clienteService;

    @Autowired
    public ClienteResource(final ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        final Cliente cliente = this.clienteService.find(id);
        return ResponseEntity.ok(cliente);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        final List<Cliente> clienteList = this.clienteService.findAll();
        final List<ClienteDTO> clienteDTOS = clienteList.stream().map(cli -> new ClienteDTO(cli)).collect(Collectors.toList());
        return ResponseEntity.ok(clienteDTOS);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
        Cliente cliente =  this.clienteService.fromDTO(clienteNewDTO);
        cliente = this.clienteService.insert(cliente);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PUT, value= "/{id}")
    public ResponseEntity<Void> update(@PathVariable final Integer id, @Valid @RequestBody final ClienteDTO categoriaDTO) {
        final Cliente categoria = this.clienteService.fromDTO(categoriaDTO);
        categoria.setId(id);
        this.clienteService.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id) {
        this.clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/paged")
    public ResponseEntity<Page<ClienteDTO>> findPaged(@RequestParam(value = "page", defaultValue = "0") final Integer page,
                                                        @RequestParam(value = "itensPerPage", defaultValue = "24") final Integer itensPerPage,
                                                        @RequestParam(value = "orderBy", defaultValue = "nome") final String orderBy,
                                                        @RequestParam(value = "direction", defaultValue = "ASC") final String direction) {
        final Page<Cliente> paged = this.clienteService.findPaged(page, itensPerPage, orderBy, direction);
        final Page<ClienteDTO> pagedDTO = paged.map(cat -> new ClienteDTO(cat));
        return ResponseEntity.ok(pagedDTO);
    }

}
