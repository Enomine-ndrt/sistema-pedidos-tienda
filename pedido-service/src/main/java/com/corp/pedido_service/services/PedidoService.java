package com.corp.pedido_service.services;

import com.corp.pedido_service.DTO.PedidoCreadoEvent;
import com.corp.pedido_service.DTO.ProductoDTO;
import com.corp.pedido_service.entities.Pedido;
import com.corp.pedido_service.feingClients.InventarioCLient;
import com.corp.pedido_service.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final InventarioCLient inventarioCLient;
    private final PedidoRepository pedidoRepository;
    private final KafkaTemplate<String, PedidoCreadoEvent> kafkaTemplate;

    public Boolean crearPedido(Long productoId, Integer cantidad){
        ProductoDTO productoDTO = inventarioCLient.obtener(productoId);
        //int partition = ThreadLocalRandom.current().nextInt(2);

        if(productoDTO.getStock() < cantidad){
            return false;
        }

        Pedido pedido = new Pedido();
        pedido.setProductoId(productoId);
        pedido.setCantidad(cantidad);
        pedido.setEstado("CREADO");

        pedidoRepository.save(pedido);

        PedidoCreadoEvent event = new PedidoCreadoEvent(
                pedido.getId(),
                pedido.getProductoId(),
                pedido.getCantidad()
        );

        kafkaTemplate.send("pedido-events",null, event)
                .whenComplete((result, ex) -> {

            if(ex != null){
                log.error("Error enviando evento Kafka", ex);
                return;
            }
            log.info("Evento enviado: {}",
                    result.getProducerRecord().value());

        });

        return true;
    }


}
