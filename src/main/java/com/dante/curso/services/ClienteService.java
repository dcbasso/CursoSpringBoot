package com.dante.curso.services;

import com.dante.curso.domain.Cliente;
import com.dante.curso.exceptions.ObjectNotFoundException;
import com.dante.curso.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(final ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente find(final Integer id) {
        final Optional<Cliente> optionalCategoria = clienteRepository.findById(id);
        return optionalCategoria.orElseThrow(() ->
                new ObjectNotFoundException("Object not found. ID: " + id + ", Type: " + Cliente.class.getName())
        );
    }

}
