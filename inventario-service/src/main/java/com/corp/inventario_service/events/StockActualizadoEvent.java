package com.corp.inventario_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockActualizadoEvent {
    private Long productoId;

    private Integer stock;
}
