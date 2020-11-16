package com.dante.curso.services;

import com.dante.curso.domain.Pedido;
import com.dante.curso.exceptions.ObjectNotFoundException;
import com.dante.curso.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(final PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido find(final Integer id) {
        final Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        return optionalPedido.orElseThrow(() ->
                new ObjectNotFoundException("Object not found. ID: " + id + ", Type: " + Pedido.class.getName())
        );
    }

}
