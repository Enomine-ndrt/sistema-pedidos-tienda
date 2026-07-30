package com.corp.inventario_service.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;


@RequiredArgsConstructor
@Log4j2
public class StrConsumerListener {

    @KafkaListener(groupId = "group-1",topics = "pedido-events",containerFactory = "validMessageContainerFactory")
    public void listenerPedido(String message){
        log.info("LISTENERPEDIDO ::: Recibiendo un pedido {} ", message);
    }

}
