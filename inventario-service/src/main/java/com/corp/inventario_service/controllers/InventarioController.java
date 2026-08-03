package com.corp.inventario_service.controllers;

import com.corp.inventario_service.dto.AgregarStockRequest;
import com.corp.inventario_service.entities.Producto;
import com.corp.inventario_service.events.StockActualizadoEvent;
import com.corp.inventario_service.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final ProductoRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id){
        return repository.findById(id).orElseThrow();
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos = repository.findAll();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<Producto> save(@RequestBody Producto producto){
        Producto nuevoProducto =  repository.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/{id}/agregar")
    public ResponseEntity<Producto> agregarStock(@PathVariable Long id, @RequestBody AgregarStockRequest request){

        if(request.getCantidad() <= 0){
            return ResponseEntity.badRequest().build();
        }

        Producto producto = repository.findById(id).orElseThrow(() ->
                        new RuntimeException("Producto no encontrado")
        );

        producto.setStock(producto.getStock() + request.getCantidad());
        Producto productoActualizado =  repository.save(producto);
        messagingTemplate.convertAndSend(
                "/topic/stock",
                new StockActualizadoEvent(
                        productoActualizado.getId(),
                        productoActualizado.getStock()
                )
        );

        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        Producto producto = repository.findById(id).orElseThrow(() ->
                        new RuntimeException("Producto no encontrado")
        );
        repository.delete(producto);
        messagingTemplate.convertAndSend(
                "/topic/producto-eliminado",
                producto.getId()
        );
        return ResponseEntity.noContent().build();
    }

}
