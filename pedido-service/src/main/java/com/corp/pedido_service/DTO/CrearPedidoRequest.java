package com.corp.pedido_service.DTO;

import lombok.Data;

@Data
public class CrearPedidoRequest {

    private Long productoId;

    private Integer cantidad;
}
