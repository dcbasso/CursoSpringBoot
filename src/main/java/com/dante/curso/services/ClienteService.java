package com.dante.curso.services;

import com.dante.curso.domain.Cliente;
import com.dante.curso.dto.ClienteDTO;
import com.dante.curso.exceptions.DataIntegrityException;
import com.dante.curso.exceptions.ObjectNotFoundException;
import com.dante.curso.repositories.ClienteRepository;
import com.dante.curso.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public ClienteService(final ClienteRepository clienteRepository, final EnderecoRepository enderecoRepository) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Cliente find(final Integer id) {
        final Optional<Cliente> optionalCategoria = clienteRepository.findById(id);
        return optionalCategoria.orElseThrow(() ->
                new ObjectNotFoundException("Object not found. ID: " + id + ", Type: " + Cliente.class.getName())
        );
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return clienteRepository.save(cliente);
    }

    public Cliente update(final Cliente cliente) {
        final Cliente toUpdateCliente = this.find(cliente.getId());
        updateData(toUpdateCliente, cliente);
        return this.clienteRepository.save(toUpdateCliente);
    }

    public void delete(final Integer id) {
        this.find(id);
        try {
            this.clienteRepository.deleteById(id);
        } catch (final DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DataIntegrityException("Was not possible to exclude Cliente");
        }
    }

    public Page<Cliente> findPaged(final Integer page, final Integer itensPerPage, final String orderBy, final String direction) {
        final PageRequest pageRequest = PageRequest.of(page, itensPerPage, Sort.Direction.valueOf(direction), orderBy);
        return this.clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(final ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    private void updateData(final Cliente toUpdateCliente, final Cliente newCliente) {
        toUpdateCliente.setNome(newCliente.getNome());
        toUpdateCliente.setEmail(newCliente.getEmail());
    }

}
