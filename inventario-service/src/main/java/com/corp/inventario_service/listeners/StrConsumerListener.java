package com.corp.inventario_service.listeners;

import com.corp.inventario_service.events.PedidoCreadoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Log4j2
@Component
public class StrConsumerListener {

    @KafkaListener(groupId = "inventario-group",
                    topics = "pedido-events-v2",
                    containerFactory = "kafkaListenerContainerFactory")
    public void listenerPedido(PedidoCreadoEvent event){
        log.info("Pedido recibido");
        log.info("ID Pedido: {}", event.getPedidoId());
        log.info("Producto: {}", event.getProductoId());
        log.info("Cantidad: {}", event.getCantidad());
    }

}
