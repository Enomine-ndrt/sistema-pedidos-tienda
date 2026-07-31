package com.corp.inventario_service.listeners;

import com.corp.inventario_service.entities.Producto;
import com.corp.inventario_service.events.PedidoCreadoEvent;
import com.corp.inventario_service.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Log4j2
@Component
public class StrConsumerListener {

    private final ProductoRepository repository;

    @KafkaListener(groupId = "inventario-group",
                    topics = "pedido-events",
                    containerFactory = "kafkaListenerContainerFactory")
    public void listenerPedido(PedidoCreadoEvent event){
        log.info("Pedido recibido");
        log.info("ID Pedido: {}", event.getPedidoId());
        log.info("Producto: {}", event.getProductoId());
        log.info("Cantidad: {}", event.getCantidad());
        Producto producto = repository
                .findById(event.getProductoId())
                .orElseThrow();

        producto.setStock(
                producto.getStock() - event.getCantidad()
        );

        repository.save(producto);

        log.info("Nuevo stock: {}", producto.getStock());
    }

}
