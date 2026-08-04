# Sistema de Pedidos - Backend (Spring Boot Microservices)

## Descripción

Sistema backend desarrollado con **Java y Spring Boot** siguiendo una arquitectura de **microservicios**.

El proyecto implementa un sistema de gestión de pedidos e inventario utilizando comunicación síncrona y asíncrona mediante **Spring Cloud**, **Kafka** y **WebSocket**, desplegado completamente con **Docker Compose** sobre **Oracle Cloud Free Tier**.

Este repositorio contiene únicamente el **backend** del sistema. El frontend desarrollado con **React** se encuentra en un repositorio independiente.

---

# Características

* Arquitectura basada en microservicios.
* API Gateway con Spring Cloud Gateway.
* Descubrimiento de servicios mediante Eureka Server.
* Comunicación asíncrona mediante Apache Kafka.
* Actualización del inventario en tiempo real mediante WebSocket.
* Persistencia con PostgreSQL.
* Despliegue mediante Docker Compose.
* Reverse Proxy con Traefik.
* Certificados HTTPS automáticos con Let's Encrypt.
* Despliegue en Oracle Cloud Free Tier.

---

# Arquitectura

```text
                        Cliente

                           │
                     HTTPS (443)

                           │
                     Traefik Proxy

                           │
                 Spring Cloud Gateway

                           │
        ┌──────────────────┴──────────────────┐
        │                                     │
        │                                     │
 Pedido Service                    Inventario Service
        │                                     │
        │                                     │
 PostgreSQL                           PostgreSQL
        │
        │
  PedidoCreadoEvent
        │
        ▼
      Apache Kafka
        │
        ▼
Inventario descuenta stock
        │
        ▼
 Spring WebSocket
        │
        ▼
Actualización en tiempo real
```

---

# Tecnologías utilizadas

## Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Web
* Spring Cloud Gateway
* Spring Cloud Netflix Eureka
* Spring Cloud OpenFeign
* Spring WebSocket
* Spring Kafka
* Maven

## Bases de datos

* PostgreSQL

## Mensajería

* Apache Kafka
* Zookeeper

## Infraestructura

* Docker
* Docker Compose
* Traefik
* Let's Encrypt
* Oracle Cloud Free Tier

---

# Microservicios

## Eureka Server

Servidor de descubrimiento de servicios.

Funciones:

* Registro automático de microservicios.
* Descubrimiento dinámico.
* Administración de instancias.

---

## Gateway Service

Punto de entrada único para el sistema.

Funciones:

* Enrutamiento de peticiones.
* Configuración CORS.
* Integración con Eureka.
* Exposición de APIs.

---

## Pedido Service

Responsable de administrar los pedidos.

Funciones:

* Crear pedidos.
* Persistir pedidos.
* Consultar inventario mediante OpenFeign.
* Publicar eventos en Kafka cuando se genera un pedido.

---

## Inventario Service

Responsable del inventario.

Funciones:

* CRUD de productos.
* Incrementar stock.
* Descontar stock al recibir eventos desde Kafka.
* Publicar eventos mediante WebSocket para actualizar clientes conectados en tiempo real.
* Notificar creación y eliminación de productos.

---

# Flujo de creación de pedidos

```text
Cliente

    │

Pedido Service

    │

Guardar Pedido

    │

PedidoCreadoEvent

    │

Apache Kafka

    │

Inventario Service

    │

Actualizar Stock

    │

WebSocket

    │

Frontend actualizado automáticamente
```

---

# Eventos implementados

## Kafka

Evento publicado:

* PedidoCreadoEvent

Evento consumido:

* PedidoCreadoEvent

---

## WebSocket

Eventos publicados:

* StockActualizadoEvent
* ProductoCreadoEvent
* ProductoEliminadoEvent

Estos eventos permiten que el frontend mantenga sincronizado el inventario sin necesidad de recargar la página.

---

# Contenedores Docker

El proyecto se ejecuta mediante Docker Compose e incluye:

* Traefik
* Eureka Server
* Gateway Service
* Pedido Service
* Inventario Service
* PostgreSQL (Pedidos)
* PostgreSQL (Inventario)
* Kafka
* Zookeeper
* Kafdrop

---

# Despliegue

El backend se encuentra preparado para ejecutarse en un servidor Linux utilizando Docker Compose.

Características del despliegue:

* HTTPS automático mediante Let's Encrypt.
* Reverse Proxy con Traefik.
* Contenedores Docker.
* Despliegue en Oracle Cloud Free Tier.

---

# Cómo ejecutar

## Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

---

## Configurar variables de entorno

Crear un archivo `.env`

Ejemplo:

```env
LETSENCRYPT_EMAIL=correo@ejemplo.com
```

---

## Crear archivo para Let's Encrypt

```bash
mkdir letsencrypt

touch letsencrypt/acme.json

chmod 600 letsencrypt/acme.json
```

---

## Iniciar los servicios

```bash
docker compose up -d
```

---

## Verificar contenedores

```bash
docker ps
```

---

# Herramientas utilizadas

* IntelliJ IDEA
* Git
* GitHub
* Docker Desktop / Docker Engine
* Oracle Cloud
* Postman
* Kafdrop

---

# Próximas mejoras

* Pruebas unitarias con JUnit y Mockito.
* Spring Security + JWT.
* Pipeline CI/CD.
* Documentación OpenAPI / Swagger.
* Observabilidad con Prometheus y Grafana.
