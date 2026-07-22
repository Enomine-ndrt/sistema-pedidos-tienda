package com.corp.pedido_service.services;

import com.corp.pedido_service.DTO.ProductoDTO;
import com.corp.pedido_service.entities.Pedido;
import com.corp.pedido_service.feingClients.InventarioCLient;
import com.corp.pedido_service.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final InventarioCLient inventarioCLient;
    private final PedidoRepository pedidoRepository;


    public Boolean crearPedido(Long productoId, Integer cantidad){
        ProductoDTO productoDTO = inventarioCLient.obtener(productoId);

        if(productoDTO.getStock() < cantidad){
            return false;
        }

        Pedido pedido = new Pedido();

        pedido.setProductoId(productoId);
        pedido.setCantidad(cantidad);
        pedido.setEstado("CREADO");

        pedidoRepository.save(pedido);
        return true;
    }


}
