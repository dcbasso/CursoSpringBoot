package com.dante.curso.resouces;

import com.dante.curso.domain.Pedido;
import com.dante.curso.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoResource(final PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> listar(@PathVariable Integer id) {
        final Pedido pedido = this.pedidoService.buscar(id);
        return ResponseEntity.ok(pedido);
    }

}
