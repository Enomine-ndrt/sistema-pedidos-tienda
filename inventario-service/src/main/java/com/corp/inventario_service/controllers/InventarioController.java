package com.corp.inventario_service.controllers;

import com.corp.inventario_service.entities.Producto;
import com.corp.inventario_service.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final ProductoRepository repository;

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

}
