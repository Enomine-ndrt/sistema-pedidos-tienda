package com.corp.pedido_service.controllers;

import com.corp.pedido_service.DTO.CrearPedidoRequest;
import com.corp.pedido_service.entities.Pedido;
import com.corp.pedido_service.repository.PedidoRepository;
import com.corp.pedido_service.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class ProductoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    @GetMapping("/{id}")
    public Pedido obtener(@PathVariable Long id){return pedidoRepository.findById(id).orElseThrow();}

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<String> crearPedido(@RequestBody CrearPedidoRequest request){

        System.out.println("Producto ID: " + request.getProductoId());
        System.out.println("Cantidad: " + request.getCantidad());
        boolean statusStock = false;
         statusStock = pedidoService.crearPedido(request.getProductoId(), request.getCantidad());

         String mensaje = "";

         if(statusStock){
             mensaje = "Pedido creado";
         }else{
             mensaje = "Pedido no creado: stock no suficiente ";
         }

        return ResponseEntity.ok(mensaje);
    }

}
