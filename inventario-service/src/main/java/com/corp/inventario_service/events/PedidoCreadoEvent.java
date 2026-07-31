package com.corp.inventario_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreadoEvent {
    private Long pedidoId;
    private Long productoId;
    private Integer cantidad;
}
