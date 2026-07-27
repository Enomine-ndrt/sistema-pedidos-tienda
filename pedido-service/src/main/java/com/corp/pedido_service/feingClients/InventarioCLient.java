package com.corp.pedido_service.feingClients;

import com.corp.pedido_service.DTO.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "inventario-service",
        url = "http://inventario-service:8000",
        path = "/inventario"
)
public interface InventarioCLient {

    @GetMapping("/{productId}")
    ProductoDTO obtener(@PathVariable("productId") Long productId);

}
