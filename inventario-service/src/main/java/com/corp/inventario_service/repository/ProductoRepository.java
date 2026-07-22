package com.corp.inventario_service.repository;

import com.corp.inventario_service.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
