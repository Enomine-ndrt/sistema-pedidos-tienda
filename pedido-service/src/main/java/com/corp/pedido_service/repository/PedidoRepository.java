package com.corp.pedido_service.repository;

import com.corp.pedido_service.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
