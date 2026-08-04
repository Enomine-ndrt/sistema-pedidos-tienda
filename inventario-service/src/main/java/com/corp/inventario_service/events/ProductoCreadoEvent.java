package com.corp.inventario_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCreadoEvent {
    private Long id;
    private String nombre;
    private Integer stock;
}
